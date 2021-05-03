package model.utility;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import model.WeatherType;
import org.junit.Before;
import org.junit.Test;

public class CheckDayOfWeekTest {
  DayOfWeek seniorDay = DayOfWeek.WEDNESDAY;

  @Test
  public void isWeekend() {
    assertTrue(CheckDayOfWeek.isWeekend(DayOfWeek.SATURDAY));
    assertTrue(CheckDayOfWeek.isWeekend(DayOfWeek.SUNDAY));
    assertFalse(CheckDayOfWeek.isWeekend(DayOfWeek.MONDAY));
  }

  @Test
  public void getDescriptors() {
    assertEquals(CheckDayOfWeek.getDescriptors(DayOfWeek.SUNDAY, seniorDay, WeatherType.IS_NICE),
        "NICE_WEEKEND");

    assertEquals(CheckDayOfWeek.getDescriptors(DayOfWeek.SUNDAY, seniorDay, WeatherType.IS_POOR),
        "POOR_WEEKEND");

    assertEquals(CheckDayOfWeek.getDescriptors(DayOfWeek.SUNDAY, seniorDay, WeatherType.IS_NEUTRAL),
        "REGULAR_WEEKEND");

    assertEquals(CheckDayOfWeek.getDescriptors(DayOfWeek.WEDNESDAY, seniorDay, WeatherType.IS_NICE),
        "SENIOR_DISCOUNT_WEEKDAY");

    assertEquals(CheckDayOfWeek.getDescriptors(DayOfWeek.MONDAY, seniorDay, WeatherType.IS_NICE),
        "REGULAR_WEEKDAY");
  }
}