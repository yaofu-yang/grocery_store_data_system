package model.utility;

import java.time.DayOfWeek;
import model.HolidayType;
import model.VisitParameters;
import model.WeatherType;

/**
 * Static class containing six static methods:
 * int applyHolidayVolume(HolidayType, int)
 * int applyPoorWeatherVolume(WeatherType, int, DayOfWeek)
 * VisitParameters applyBeforeHoliday(HolidayType, int)
 * VisitParameters applyNiceWeather(int)
 * VisitParameters applyMealRush(int, int, int)
 * VisitParameters applySeniorDiscount(int, int, int)
 */
public final class Modifier {
  private Modifier() {}

  /**
   * Given the following two parameters, returns an int representing the new daily volume with
   * any holiday modifications applied (if applicable).
   * @param holiday - HolidayType instance representing the holiday modification.
   * @param original - int representing the original total customers in a day.
   * @return an int representing the new daily volume with any holiday modifications applied
   * (if applicable).
   */
  public static int applyHolidayVolume(HolidayType holiday, int original) {
    if (holiday == HolidayType.IS_HOLIDAY) {
      final double HOLIDAY_FACTOR = 1.20;
      return (int) (original * HOLIDAY_FACTOR);  // Modified.
    }
    return original;  // Unmodified.
  }

  /**
   * Given the following parameters, returns an int representing the new daily customer volume with
   * the poor weather effect applied (if applicable)
   * @param weather - WeatherType representing the weather modifier.
   * @param original - int representing the original total customer volume in a day.
   * @param dayOfWeek - DayOfWeek instance.
   * @return an int representing the new daily customer volume with the poor weather effect applied
   * (if applicable)
   */
  public static int applyPoorWeatherVolume(WeatherType weather, int original, DayOfWeek dayOfWeek) {
    if (CheckDayOfWeek.isWeekend(dayOfWeek)) {
      final double POOR_WEATHER_FACTOR = 0.7;
      if (weather == WeatherType.IS_POOR) {
        return (int) (original * POOR_WEATHER_FACTOR);  // Modified.
      }
    }
    return original;  // Unmodified.
  }

  /**
   * Given the following parameters, returns a VisitParameters instance for additional visits that
   * take into account possible effects for days before a holiday.
   * @param holiday - HolidayType representing the holiday modifier.
   * @param original - int representing the original total customer volume on this day.
   * @return a VisitParameters instance for a particular visit that takes into account possible
   * effects for days before a holiday.
   */
  public static VisitParameters applyBeforeHoliday(HolidayType holiday, int original) {
    final double DAY_BEFORE_HOLIDAY_BOOST = 0.40;
    final double WEEK_TO_HOLIDAY_BOOST = 0.15;
    final double[] entryDist = {0.003, 0.005, 0.03, 0.05, 0.06, 0.07,
            0.08, 0.085, 0.09, 0.095, 0.095, 0.095, 0.097, 0.085, 0.06};
    final double[] durationDist = {0, 0.1 , 0.2, 0.25, 0.3, 0.15};

    // Tertiary structure to determine which boost to apply. Only two possible cases.
    double boost = (holiday == HolidayType.DAY_BEFORE_HOLIDAY) ?
        DAY_BEFORE_HOLIDAY_BOOST : WEEK_TO_HOLIDAY_BOOST;
    int additionalVolume = (int) (original * boost);
    return new VisitParameters(additionalVolume, entryDist, durationDist);
  }

  /**
   * Given the following parameter, returns a VisitParameters instance for additional visits that
   * take into account a possible nice weather effect.
   * @param original - int representing the original customer volume for this day.
   * @return a VisitParameters instance for a particular visit that takes into account a possible
   * nice weather effect.
   */
  public static VisitParameters applyNiceWeather(int original) {
        final double NICE_WEATHER_BOOST = 0.4;
        double[] durationDist = {.50, .30, .15, .0125, .015, .0125};
        double[] entryDist = {0, 0, 0, 0, .1, .1, .2, .1, 0, 0, .1, .2, .2, 0, 0};
        int additionalVolume = (int) (original * NICE_WEATHER_BOOST);
        return new VisitParameters(additionalVolume, entryDist, durationDist);
  }


  /**
   * Given the following parameters, returns a VisitParameters instance for additional visits that
   * take into account the lunch and dinner rush hours.
   * @param original - int representing the original customer volume for this day.
   * @param lunch - int representing the starting hour for expected lunch rush hour.
   * @param dinner - int representing the starting hour for expected dinner rush hour.
   * @return a VisitParameters instance for a particular visit that takes into account the lunch
   * and dinner rush hours.
   */
  public static VisitParameters applyMealRush(int original, int lunch, int dinner) {
    // Assumption: Lunch/dinner rushes are one hour only
    final double MEAL_BOOST = 0.3;
    final int STORE_OPENING = 6;
    final double LUNCH_PERCENTAGE = 0.4;
    final double DINNER_PERCENTAGE = 0.6;

    double[] durationDist = {1.0, 0, 0, 0, 0, 0};
    double[] entryDist = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    entryDist[lunch - STORE_OPENING] = LUNCH_PERCENTAGE;  // 40% additional visits are for lunch
    entryDist[dinner - STORE_OPENING] = DINNER_PERCENTAGE;  // 60% additional visits are for dinner.
    int additionalVolume = (int) (original * MEAL_BOOST);
    return new VisitParameters(additionalVolume, entryDist, durationDist);
  }

  /**
   * Given the following parameters, returns a VisitParameters instance for additional visits that
   * take into account the possible effects of a senior discount interval.
   * @param original - int representing the original customer volume for this day.
   * @param startHour - int representing the starting hour (inclusive) of the senior discount.
   * @param endHour - int representing the ending hour (exclusive) of the senior discount.
   * @return a  VisitParameters instance for a particular visit that takes into account the possible
   * effects of a senior discount interval.
   */
  public static VisitParameters applySeniorDiscount(int original, int startHour, int endHour) {
    // Assumptions: Start/end hours are full hours && senior discounts are two hours.
    final double SENIOR_BOOST = 0.3;
    final int STORE_OPENING = 6;
    double[] durationDist = {0, 0, 0, .45, .5, .05};
    double[] entryDist = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    // Update the specified entry time based on start/end hours from parameters.
    for (int i = startHour - STORE_OPENING; i < endHour - STORE_OPENING; i++) {
      entryDist[i] = 0.5;
    }
    int additionalVolume = (int) (original * SENIOR_BOOST);
    return new VisitParameters(additionalVolume, entryDist, durationDist);
  }
}
