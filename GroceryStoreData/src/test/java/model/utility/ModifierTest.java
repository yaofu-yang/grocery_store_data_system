package model.utility;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import model.HolidayType;
import model.VisitParameters;
import model.WeatherType;
import org.junit.Before;
import org.junit.Test;

public class ModifierTest {

  /**
   *  * Static class containing six static methods:
   *  * int applyHolidayVolume(HolidayType, int)
   *  * int applyPoorWeatherVolume(WeatherType, int, DayOfWeek)
   *  * VisitParameters applyBeforeHoliday(HolidayType, int)
   *  * VisitParameters applyNiceWeather(int)
   *  * VisitParameters applyMealRush(int, int, int)
   *  * VisitParameters applySeniorDiscount(int, int, int)
   */
  double[] holidayEntry = {0.003, 0.005, 0.03, 0.05, 0.06, 0.07,
      0.08, 0.085, 0.09, 0.095, 0.095, 0.095, 0.097, 0.085, 0.06};
  double[] holidayDuration = {0, 0.1 , 0.2, 0.25, 0.3, 0.15};

  double[] weatherDuration = {.50, .30, .15, .0125, .015, .0125};
  double[] weatherEntry = {0, 0, 0, 0, .1, .1, .2, .1, 0, 0, .1, .2, .2, 0, 0};

  double[] mealDuration = {1.0, 0, 0, 0, 0, 0};
  double[] mealEntry = {0, 0, 0, 0, 0, 0, .4, 0, 0, 0, .6, 0, 0, 0, 0};

  double[] seniorDuration = {0, 0, 0, .45, .5, .05};
  double[] seniorEntry = {0, 0, 0, 0, 0, 0, 0, .5, .5, 0, 0, 0, 0, 0, 0};

  int original = 1000;

  @Before
  public void setUp() {
  }

  @Test
  public void applyHolidayVolume() {
    assertEquals(Modifier.applyHolidayVolume(HolidayType.IS_HOLIDAY, original), 1200);
    assertEquals(Modifier.applyHolidayVolume(HolidayType.NON_HOLIDAY, original), 1000);

  }

  @Test
  public void applyPoorWeatherVolume() {
    // Weekend and poor weather.
    assertEquals(Modifier.applyPoorWeatherVolume(WeatherType.IS_POOR, original, DayOfWeek.SUNDAY),
        700);
    // Poor weather but not weekend.
    assertEquals(Modifier.applyPoorWeatherVolume(WeatherType.IS_POOR, original, DayOfWeek.MONDAY),
        1000);
    // Weekend but not poor weather.
    assertEquals(Modifier.applyPoorWeatherVolume(WeatherType.IS_NICE, original, DayOfWeek.SUNDAY),
        1000);
  }

  @Test
  public void applyBeforeHoliday() {
    VisitParameters dayBeforeH = new VisitParameters(400, holidayEntry, holidayDuration);
    VisitParameters weekToH = new VisitParameters(150, holidayEntry, holidayDuration);

    assertEquals(Modifier.applyBeforeHoliday(HolidayType.DAY_BEFORE_HOLIDAY, original), dayBeforeH);
    assertEquals(Modifier.applyBeforeHoliday(HolidayType.WEEK_TO_HOLIDAY, original), weekToH);
  }

  @Test
  public void applyNiceWeather() {
    VisitParameters extraVisits = new VisitParameters(400, weatherEntry, weatherDuration);
    assertEquals(Modifier.applyNiceWeather(original), extraVisits);
  }

  @Test
  public void applyMealRush() {
    VisitParameters extraVisits = new VisitParameters(300, mealEntry, mealDuration);
    assertEquals(Modifier.applyMealRush(original, 12, 16), extraVisits);
  }

  @Test
  public void applySeniorDiscount() {
    VisitParameters extraVisits = new VisitParameters(300, seniorEntry, seniorDuration);
    assertEquals(Modifier.applySeniorDiscount(original, 13, 15), extraVisits);
  }
}