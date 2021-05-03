package model.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates an instance of the Constant class which contains collections of data to represent the
 * visit volume of a particular day, visit volume distribution over a course of a particular day,
 * and visit duration distribution over the course of a day.
 */
public class Constant {
    private Map<String, Integer> amountOfCustomers;
    private Map<String, double[]> entryTimeDist;
    private Map<String, Map<String, double[]>> durationTimeDist;

    /**
     * Constructs an instance of the Constant class without accepting any parameters.
     * All data values are pre-loaded upon instantiation.
     */
    public Constant() {
        // load total amount of customers per Day
        this.amountOfCustomers = new HashMap<>();
        this.amountOfCustomers.put("Monday", 620);
        this.amountOfCustomers.put("Tuesday", 650);
        this.amountOfCustomers.put("Wednesday", 930);
        this.amountOfCustomers.put("Thursday", 700);
        this.amountOfCustomers.put("Friday", 2200);
        this.amountOfCustomers.put("Saturday", 4000);
        this.amountOfCustomers.put("Sunday", 5000);
        this.amountOfCustomers.put("NiceWeekend", 6300);

        // load entry time distributions
        this.entryTimeDist = new HashMap<>();

        // This is the updated distribution, split into weekday/weekend.
        this.entryTimeDist.put("Weekday", new double[]{0.005, 0.025, 0.03, 0.05, 0.07, 0.07, 0.08,
            0.08, 0.08, 0.09, 0.09, 0.1, 0.1, 0.08, 0.05});
        this.entryTimeDist.put("Weekend", new double[]{0.003, 0.005, 0.03, 0.05, 0.06, 0.07,
            0.08, 0.085, 0.09, 0.095, 0.095, 0.095, 0.097, 0.085, 0.06});

        // Loads duration time distributions
        this.durationTimeDist = new HashMap<>();
        Map<String, double[]> sixToEight = new HashMap<>();
        sixToEight.put("Weekday", new double[]{0.35, 0.55, 0.1, 0, 0, 0});
        sixToEight.put("Friday", new double[]{0.35, 0.55, 0.1, 0, 0, 0});
        sixToEight.put("Weekend", new double[]{0.35, 0.55, 0.1, 0, 0, 0});
        sixToEight.put("Holiday", new double[]{0.35, 0.55, 0.1, 0, 0, 0});
        this.durationTimeDist.put("6-8", sixToEight);

        Map<String, double[]> eightToOne = new HashMap<>();
        eightToOne.put("Weekday", new double[]{0.3, 0.5, 0.15, 0.05, 0, 0});
        eightToOne.put("Friday", new double[]{0.3, 0.5, 0.15, 0.05, 0, 0});
        eightToOne.put("Weekend", new double[]{0, 0.2, 0.35, 0.3, 0.15, 0});
        eightToOne.put("Holiday", new double[]{0, 0.2, 0.35, 0.3, 0.15, 0});
        this.durationTimeDist.put("8-13", eightToOne);

        Map<String, double[]> oneToSeven = new HashMap<>();
        oneToSeven.put("Weekday", new double[]{0.25, 0.4, 0.25, 0.05, 0.05, 0});
        oneToSeven.put("Friday", new double[]{0.2, 0.35, 0.3, 0.1, 0.05, 0});
        oneToSeven.put("Weekend", new double[]{0, 0.1, 0.15, 0.25, 0.30, .2});
        oneToSeven.put("Holiday", new double[]{0, 0.1, 0.15, 0.25, 0.30, .2});
        this.durationTimeDist.put("13-19", oneToSeven);

        // Temporarily included data under 19:00-20:59 range.
        Map<String, double[]> sevenToNine = new HashMap<>();
        sevenToNine.put("Weekday", new double[]{0.3, 0.45, 0.2, 0.05, 0, 0});
        sevenToNine.put("Friday", new double[]{0.15, 0.3, 0.35, 0.1, 0.05, 0.05});
        sevenToNine.put("Weekend", new double[]{0, 0.1, 0.2, 0.25, 0.30, .15});
        sevenToNine.put("Holiday", new double[]{0, 0.1, 0.2, 0.25, 0.30, .15});
        this.durationTimeDist.put("19-21", sevenToNine);
    }

    /**
     * Returns a Map with key: String and value: Integer representing the amount of customers
     * on different days of the week.
     * @return a Map with key: String and value: Integer representing the amount of customers
     * on different days of the week.
     */
    public Map<String, Integer> getAmountOfCustomers() {
        return amountOfCustomers;
    }

    /**
     * Returns a Map with key: String and value: array of double that represents the collection of
     * entry time distributions for customers on different days of the week.
     * @return a Map with key: String and value: array of double that represents the collection of
     * entry time distributions for customers on different days of the week.
     */
    public Map<String, double[]> getEntryTimeDist() {
        return entryTimeDist;
    }

    /**
     * Returns a Map with key: String and value: Map that represents the duration distributions at
     * different intervals of a day for different days of the week.
     * @return a Map with key: String and value: Map that represents the duration distributions at
     * different intervals of a day for different days of the week.
     */
    public Map<String, Map<String, double[]>> getDurationTimeDist() {
        return durationTimeDist;
    }
}
