package model.utility;

import model.HolidayType;
import model.data.Constant;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Static class containing methods to get the default daily volume, apply holiday volume, apply
 * nice weather volume, get entry time distribution data, and get duration distribution data.
 * Cannot create an instance of this class.
 */
public final class DistributionDeterminer {
    private DistributionDeterminer() {}

    /**
     * Given a LocalDate representing a visit date, returns an int representing the default daily
     * volume from the Constant instance, data.
     * @param date - LocalDate instance representing the visit date.
     * @param data - Constant instance containing data for default daily volumes by day.
     * @return an int representing the default daily volume from the Constant instance, data.
     */
    public static int getDailyVolume(LocalDate date, Constant data) {
        int DAILY_VOLUME = 0;
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if(dayOfWeek== DayOfWeek.MONDAY) {
            DAILY_VOLUME = data.getAmountOfCustomers().get("Monday");
        } else if(dayOfWeek == DayOfWeek.TUESDAY) {
            DAILY_VOLUME = data.getAmountOfCustomers().get("Tuesday");
        } else if(dayOfWeek == DayOfWeek.WEDNESDAY) {
            DAILY_VOLUME = data.getAmountOfCustomers().get("Wednesday");
        } else if(dayOfWeek == DayOfWeek.THURSDAY) {
            DAILY_VOLUME = data.getAmountOfCustomers().get("Thursday");
        } else if(dayOfWeek == DayOfWeek.FRIDAY) {
            DAILY_VOLUME = data.getAmountOfCustomers().get("Friday");
        } else if(dayOfWeek == DayOfWeek.SATURDAY) {
            DAILY_VOLUME = data.getAmountOfCustomers().get("Saturday");
        } else if(dayOfWeek == DayOfWeek.SUNDAY) {
            DAILY_VOLUME = data.getAmountOfCustomers().get("Sunday");
        }
        return DAILY_VOLUME;
    }

    /**
     * Given an int representing the visit id, a LocalDate instance representing the date, and a
     * Constant instance representing the data source, returns a LocalDateTime instance representing
     * the date and time of this visit.
     * @param id - int representing the unique visit.
     * @param date - LocalDate instance representing the visit date.
     * @param data - Constant instance containing entry distribution data.
     * @return a LocalDateTime instance representing the date and time of this visit.
     */
    public static LocalDateTime getEntryTime(int id, LocalDate date, Constant data) {
        LocalDateTime ldt;
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        // Todo (all): Review refactor. Modified to just weekday/weekend split for entry times.
        if (CheckDayOfWeek.isWeekend(dayOfWeek)) {
            ldt = RandomGenerator.generateEntryData(id, data.getEntryTimeDist().get("Weekend"));
        } else {
            ldt = RandomGenerator.generateEntryData(id, data.getEntryTimeDist().get("Weekday"));
        }
        return ldt;
    }

    /**
     * Given a LocalDateTime instance representing a particular visit, returns the appropriate
     * duration distribution from the given Constant instance.
     * @param ldt - LocalDateTime instance representing a visit on a specified date and time.
     * @param data - Constant instance containing all duration distributions.
     * @param holiday - HolidayType representing holiday modifier.
     * @return the appropriate duration distribution from the given Constant instance.
     */
    public static double[] getDurationDistribution(LocalDateTime ldt, Constant data,
        HolidayType holiday) {

        double[] durationDist;
        int shoppingHour = ldt.getHour();
        DayOfWeek dayOfWeek = ldt.getDayOfWeek();

        if (holiday != HolidayType.NON_HOLIDAY) {
            durationDist = getDistributionSubset("Holiday", data, shoppingHour);
        } else if (dayOfWeek == DayOfWeek.FRIDAY) {
            durationDist = getDistributionSubset("Friday", data, shoppingHour);
        } else if(CheckDayOfWeek.isWeekend(dayOfWeek)) {
            durationDist = getDistributionSubset("Weekday", data, shoppingHour);
        } else {
            durationDist = getDistributionSubset("Weekday", data, shoppingHour);
        }
        return durationDist;
    }

    /**
     * Helper method.
     * Given the shopping hour, retrieve the correct distribution subset for that hour from the
     * given Constant model using the given String, key. Returns an array of doubles representing
     * the subset of distribution for the given hour.
     * @param key - String representing the key to retrieve data for a particular day of the week.
     * @param data - Instance of the Constant class that contains all duration distributions.
     * @param hour - int representing the hour of this particular visit.
     * @return an array of doubles representing the subset of distribution for the given hour.
     */
    private static double[] getDistributionSubset(String key, Constant data, int hour) {
        double[] distributionSubset;
        if (hour < 8) {
            distributionSubset = data.getDurationTimeDist().get("6-8").get(key);
        } else if(hour < 13) {
            distributionSubset = data.getDurationTimeDist().get("8-13").get(key);
        } else if(hour < 19) {
            distributionSubset = data.getDurationTimeDist().get("13-19").get(key);
        } else {
            distributionSubset = data.getDurationTimeDist().get("19-21").get(key);
        }
        return distributionSubset;
    }
}
