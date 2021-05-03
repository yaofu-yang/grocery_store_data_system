package view;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.*;
import model.DateTime;
import model.Day;
import model.Visit;
import model.utility.CheckDayOfWeek;

/**
 * Creates an instance of the CsvGenerator class that contains one method, writeToCsv, which accepts
 * a list of Day instances and generates a csv document "products" that contains all of the
 * individual visit information per row.
 */
public class CsvGenerator {

    /**
     * Given a list of Day instances that comprise a full month of interest, generate a CSV file
     * where each row represents the important information of a shopper visit.
     * @param dayList - List of Day instances for a month of interest.
     */
    public void writeToCSV(List<Day> dayList) {
        // Highly referenced: https://stackoverflow.com/questions/3666007/how-to-serialize-object-to-csv-file

        final String CSV_SEPARATOR = ",";
        final DayOfWeek SENIOR_DISCOUNT_DAY = DayOfWeek.WEDNESDAY;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("products.csv"), "UTF-8"));
            StringBuilder oneLine = new StringBuilder();
            oneLine.append("VisitID");
            oneLine.append(CSV_SEPARATOR);
            oneLine.append("EntryTime");
            oneLine.append(CSV_SEPARATOR);
            oneLine.append("LeaveTime");
            oneLine.append(CSV_SEPARATOR);
            oneLine.append("Duration(minute)");
            oneLine.append(CSV_SEPARATOR);
            oneLine.append("HolidayType");
            oneLine.append(CSV_SEPARATOR);
            oneLine.append("DayOfWeek");
            oneLine.append(CSV_SEPARATOR);
            oneLine.append("DayDescription");
            bw.write(oneLine.toString());
            bw.newLine();
            for (Day day : dayList) {
                for (Visit visit : day.getVisits())
                {
                    DateTime entry = visit.getEntryTime();
                    oneLine = new StringBuilder();
                    oneLine.append(visit.getVisitID());
                    oneLine.append(CSV_SEPARATOR);
                    // here replace the year with 2020
                    oneLine.append(entry.getLocalDateTime().format(formatter));
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(visit.getLeaveTime().getLocalDateTime().format(formatter));
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(visit.getDuration());
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(entry.getHolidayType());
                    oneLine.append(CSV_SEPARATOR);
                    DayOfWeek dayOfWeek = entry.getLocalDateTime().getDayOfWeek();
                    oneLine.append(dayOfWeek);
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(CheckDayOfWeek.getDescriptors(
                        dayOfWeek, SENIOR_DISCOUNT_DAY, entry.getWeather().getWeatherType()));
                    bw.write(oneLine.toString());
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
        } catch (IOException e){
            System.out.println("I/O exception");
        }
    }
}
