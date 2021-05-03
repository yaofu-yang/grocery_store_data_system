package controller;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import dao.MetaDao;
import model.*;
import model.data.Constant;
import model.utility.Modifier;
import model.utility.*;
import view.CsvGenerator;
import model.Visit;

/**
 * Controller class that contains a main method that instantiates all of the required objects.
 * Does not require any command line arguments and generates a csv document displaying all of the
 * highlighted visit information per row.
 */
public class PilotSim {
    private static final int MONTH = 5;
    private static final int DAYS_IN_MONTH = 31;
    private static final int WEATHER_SAMPLE_TIME = 12;
    private static final DayOfWeek SENIOR_DISCOUNT_DAY = DayOfWeek.WEDNESDAY;
    private static final int SENIOR_DISCOUNT_START = 13;
    private static final int SENIOR_DISCOUNT_END = 15;
    private static final int LUNCH_PEAK = 12;
    private static final int DINNER_PEAK = 16;

    private static CsvGenerator csvGenerator = new CsvGenerator();
    private static WeatherParser weatherParser = new WeatherParser();

    /**
     * main method that does not require any command line arguments.
     * @param args - command line arguments; not required for this method.
     */
    public static void main(String[] args) {
        List<Day> days = new ArrayList<>();
        Constant constant = new Constant();
        int dailyVolume;

        for(int i = 1; i <= DAYS_IN_MONTH; i++) {

            Day newDay = new Day();
            // Initialize a new date in the target range and determine customer volume that day.
            LocalDate date = LocalDate.of(2020, MONTH, i);
            HolidayType holiday = HolidayDeterminer.getHolidayInfo(date);
            WeatherType weatherType = weatherParser.findWeather(date.atTime(WEATHER_SAMPLE_TIME, 0))
                .getWeatherType();  // Arbitrarily chosen at noontime.
            DayOfWeek dayOfWeek = date.getDayOfWeek();


            // Get baseline volume (normal days)
            dailyVolume = DistributionDeterminer.getDailyVolume(date, constant);

            // Apply holiday volume modifications.
            dailyVolume = Modifier.applyHolidayVolume(holiday, dailyVolume);
            // Apply poor weather volume modifications.
            dailyVolume = Modifier.applyPoorWeatherVolume(weatherType, dailyVolume, dayOfWeek);

            // Get baseline data (normal days) OR holiday data OR poor weather data
            for (int j = 0; j < dailyVolume; j++) {
                // Get entry information.
                LocalDateTime ldt = DistributionDeterminer.getEntryTime(i, date, constant);

                // Get visit duration distribution for the specified date/time.
                double[] durationDist = DistributionDeterminer.getDurationDistribution(
                        ldt, constant, holiday);
                Weather weather = weatherParser.findWeather(ldt);

                // add pre-fix "N" representing normal daily volume ID
                String id = "N" + (i - 1) * dailyVolume + j;
                // Get entry information including weather and holiday information.
                DateTime entryTime = new DateTime(ldt, weather, holiday);

                // get the visit duration in minutes.
                int totalMinutes = RandomGenerator.generateDuration(durationDist);

                // Get visit leave information.
                LocalDateTime leaveTime = ldt.plusMinutes(totalMinutes);
                DateTime leaveDateTime = new DateTime(leaveTime, weatherParser.findWeather(leaveTime),
                        HolidayDeterminer.getHolidayInfo(date));

                // Immutable creation of visit.
                Visit visit = new Visit(id, entryTime, leaveDateTime, totalMinutes);
                newDay.addVisit(visit);
            }

            // add event data (except Holiday and bad weather)
            // Apply day/week before holiday effect.
            if (holiday == HolidayType.DAY_BEFORE_HOLIDAY || holiday == HolidayType.WEEK_TO_HOLIDAY ) {
                VisitParameters beforeHoliday = Modifier.applyBeforeHoliday(holiday, dailyVolume);
                String prefixID = "H";
                List<Visit> newVisits = getExtraVisits(i, beforeHoliday, prefixID, holiday, date);
                newDay.mergeVisits(newVisits);
            }

            // Apply the nice weather effect.
            if (weatherType == WeatherType.IS_NICE && CheckDayOfWeek.isWeekend(dayOfWeek)) {
                VisitParameters niceWeather = Modifier.applyNiceWeather(dailyVolume);
                String prefixID = "W";
                List<Visit> newVisits = getExtraVisits(i, niceWeather, prefixID, holiday, date);
                newDay.mergeVisits(newVisits);
            }

            // Apply the meal hour effect.
            if (holiday != HolidayType.IS_HOLIDAY && !CheckDayOfWeek.isWeekend(dayOfWeek)) {
                // Apply the meal rush effects.
                VisitParameters mealRush = Modifier.applyMealRush(dailyVolume, LUNCH_PEAK,
                    DINNER_PEAK);
                String prefixID = "M";
                List<Visit> newVisits = getExtraVisits(i, mealRush, prefixID, holiday, date);
                newDay.mergeVisits(newVisits);
            }

            // Apply the senior discount effect.
            if (holiday != HolidayType.IS_HOLIDAY && dayOfWeek == SENIOR_DISCOUNT_DAY) {
                VisitParameters seniorTime = Modifier.applySeniorDiscount(dailyVolume,
                    SENIOR_DISCOUNT_START, SENIOR_DISCOUNT_END);
                String prefixID = "S";
                List<Visit> visits = getExtraVisits(i, seniorTime, prefixID, holiday, date);
                newDay.mergeVisits(visits);
            }
            days.add(newDay);
        }

        // Sorts all visits by entry date/time information.
        for (Day day : days) {
            day.getVisits().sort(new Comparator<Visit>() {
                @Override
                public int compare(Visit v1, Visit v2) {
                    return v1.getEntryTime().getLocalDateTime()
                        .compareTo(v2.getEntryTime().getLocalDateTime());
                }
            });
        }
        csvGenerator.writeToCSV(days);

        // Calls Database (MongoDB) functions. May comment out to test code functionality.

        //truncate previous results
        // Todo: Maybe modify in UI to make this an option.
        MetaDao.truncateData();

        // initialize a DataSet object
//        DataSet ds = new DataSet();

        // assign its corresponding Visits
        List<Visit> visits = new ArrayList<>();
        for(Day d:days) {
            visits.addAll(d.getVisits());
        }

        MetaDao.addAllEntries(visits);

//        ds.setVisits(visits);
//
//        // set its Month
//        ds.setMonth(Month.MAY);
//
//        // set its generated time
//        ds.setGeneratedTime(LocalDateTime.now());
//
//        // insert it into database
//        List<DataSet> dataSets = new ArrayList<>();
//        dataSets.add(ds);
//        MetaDao.addAllDataSets(dataSets);
    }

    /**
     * Given the following parameters, returns a list of Visit objects representing the additional
     * visits due to special modifiers of a particular day.
     * @param day - int representing the day of the month.
     * @param visitParameters - VisitParameters instance representing information for extra visits.
     * @param prefix - String representing prefix to visit id.
     * @param holiday - HolidayType representing holiday modifier.
     * @param date - LocalDate instance representing date of day of interest.
     * @return a list of Visit objects representing the additional visits due to special modifiers
     * of a particular day.
     */
    public static List<Visit> getExtraVisits(int day, VisitParameters visitParameters,
                                          String prefix, HolidayType holiday, LocalDate date) {

        int volume = visitParameters.getAdditionalVolume();
        double[] entryDist = visitParameters.getEntryDist();
        double[] durationDist = visitParameters.getDurationDist();

        List<Visit> newVisits = new ArrayList<>();
        for (int i = 0; i < volume; i++) {
            // Get entry information.
            LocalDateTime ldt = RandomGenerator.generateEntryData(day, entryDist);

            // Get visit duration distribution for the specified date/time.
            Weather weather = weatherParser.findWeather(ldt);

            // id add pre-code representing normal data, day/week before holiday, niceWeather, mealPeak, seniorDiscount
            String id = String.valueOf(prefix + "0" + i);
            // Get entry information including weather and holiday information.
            DateTime entryTime = new DateTime(ldt, weather, holiday);

            // get the visit duration in minutes.
            int totalMinutes = RandomGenerator.generateDuration(durationDist);

            // Get visit leave information.
            LocalDateTime leaveTime = ldt.plusMinutes(totalMinutes);
            DateTime leaveDateTime = new DateTime(leaveTime, weatherParser.findWeather(leaveTime),
                    HolidayDeterminer.getHolidayInfo(date));

            // Immutable creation of visit.
            Visit visit = new Visit(id, entryTime, leaveDateTime, totalMinutes);
            newVisits.add(visit);
        }
        return newVisits;
    }
}
