package model.utility;

import java.time.DayOfWeek;
import model.WeatherType;

/**
 * Static class containing two static methods:
 * isWeekend(DayOfWeek) that returns true if the provided argument is a weekend.
 * getDescriptors(DayOfWeek, DayOfWeek, WeatherType) that returns a String describing the particular
 * day based on these parameters.
 */
public final class CheckDayOfWeek {
  private CheckDayOfWeek() {}

  /**
   * Given a DayOfWeek instance, returns true if it is a weekend, false otherwise.
   * @param dayOfWeek - DayOfWeek instance representing a day.
   * @return true if day of week is a weekend, false otherwise.
   */
  public static boolean isWeekend(DayOfWeek dayOfWeek) {
    return (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
  }

  /**
   * Given the current day of week, the expected senior discount day per week, and weather condition
   * for the day, returns a String representing additional descriptions for a visit.
   * @param thisDay - DayOfWeek representing the current day.
   * @param seniorDay - DayOfWeek representing the senior discount day.
   * @param weather - WeatherType instance representing the weather niceness/poorness
   * @return a String representing additional descriptions for a visit.
   */
  public static String getDescriptors(DayOfWeek thisDay, DayOfWeek seniorDay, WeatherType weather) {
    if (CheckDayOfWeek.isWeekend(thisDay)) {
      switch(weather) {
        case IS_NICE:
          return "NICE_WEEKEND";
        case IS_POOR:
          return "POOR_WEEKEND";
        default:
          return "REGULAR_WEEKEND";
      }
    } else if (thisDay == seniorDay) {
      return "SENIOR_DISCOUNT_WEEKDAY";
    } else {
      return "REGULAR_WEEKDAY";
    }
  }
}
