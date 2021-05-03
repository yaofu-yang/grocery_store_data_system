package model.utility;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Static class that contains two public static methods to generate the visit entry information and
 * the visit duration information.
 */
public final class RandomGenerator {
  private RandomGenerator() {}

  /**
   * Given the following parameters, returns a LocalDateTime instance representing the entry
   * information of a visit.
   * @param visitDay - An int representing the day of a month that the visit occurred.
   * @param visitDist - An array of doubles representing the visit distribution by percentage.
   * @return a LocalDateTime instance representing the entry information of a visit.
   */
  public static LocalDateTime generateEntryData(int visitDay, double[] visitDist) {
    final int MINUTES_PER_HOUR = 60;
    final int FULL_PERCENTAGE = 100;
    final int VISIT_YEAR = 2020;  // Make this a parameter?
    final Month month = Month.MAY;  // Make this a parameter?

    int visitPercentage = randomNumberGenerator(FULL_PERCENTAGE);
    int entryHour = findHour(visitPercentage, getVisitCumulative(visitDist));
    int entryMinute = randomNumberGenerator(MINUTES_PER_HOUR);

    return LocalDateTime.of(VISIT_YEAR, month, visitDay, entryHour, entryMinute);
  }

  /**
   * Given the duration distribution, returns a random number of duration minutes.
   * @param dist - A double array representing the distribution of duration.
   * @return an int representing the duration(in minute) of a visit.
   */
  public static int generateDuration(double[] dist) {

    int[][] intervals = {{6, 20}, {21, 30}, {31, 40}, {41, 50}, {51, 60}, {61, 75}};
    List<Integer> samples = new ArrayList<>();

    for(int i=0; i < dist.length; i++) {
      for(int j=0; j < dist[i]*1000; j++) {
        samples.add(i);
      }
    }

    int idx = samples.get(randomNumberGenerator(samples.size()));
    return randomNumberGenerator(intervals[idx][1] - intervals[idx][0] + 1) + intervals[idx][0];
  }

  /**
   * Returns a pseudo-randomly generated int between 0 and non-inclusive range.
   * @param range - An int representing the non-inclusive end range of a random number.
   * @return a pseudo-randomly generated int between 0 and non-inclusive range.
   */
  private static int randomNumberGenerator(int range) {
    Random rand = new Random();
    return rand.nextInt(range);
  }

  /**
   * Given an array of doubles representing a one-day visit distribution by percentage, returns an
   * array of doubles representing the cumulative visits by hour for the day.
   * @param visitDist - Array of doubles representing the visit distribution by percentage.
   * @return an array of doubles representing the cumulative visits by hour for the day.
   */
  private static double[] getVisitCumulative(double[] visitDist) {
    final int TOTAL_STORE_HOURS = 15;
    final int PERCENT_CONVERSION_FACTOR = 100;

    double[] visitCumulative = new double[TOTAL_STORE_HOURS];
    visitCumulative[0] = visitDist[0] * PERCENT_CONVERSION_FACTOR;

    for (int i = 1; i < visitDist.length; i++) {
      visitCumulative[i] = visitCumulative[i - 1] + visitDist[i] * PERCENT_CONVERSION_FACTOR;
    }
    return visitCumulative;
  }

  /**
   * Given an int representing the random distribution threshold and a double array representing the
   * cumulative visits, returns an int representing the hour of a particular visit.
   * @param num - An int representing the random distribution threshold.
   * @param visitCumulative - double array representing cumulative visit totals per hour.
   * @return an int representing the hour of a particular visit.
   */
  private static int findHour(int num, double[] visitCumulative) {
    final int STORE_OPENING_HOUR = 6;
    final int DEFAULT_RETURN = -1;

    for (int i = 0; i < visitCumulative.length; i++) {
      if (num < visitCumulative[i]) {
        return i + STORE_OPENING_HOUR; // store opens at 6
      }
    }
    System.out.println("Check the visit distribution, it was over 100%!!!");
    return DEFAULT_RETURN;
  }
}
