package model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class DateTimeTest {

  LocalDateTime date1;
  LocalDateTime date2;
  Weather weather1;
  Weather weather2;

  DateTime test1;
  DateTime test2;
  DateTime test3;
  DateTime test4;
  DateTime test5;

  @Before
  public void setUp() {
    weather1 = new Weather(WeatherType.IS_NICE, 65.0);
    weather2 = new Weather(WeatherType.IS_NEUTRAL, 65.0);

    date1 = LocalDateTime.of(2020, 5, 1, 6, 12);
    date2 = LocalDateTime.of(2020, 5, 1, 6, 20);

    test1 = new DateTime(date1, weather1, HolidayType.NON_HOLIDAY);
    test2 = new DateTime(date1, weather1, HolidayType.NON_HOLIDAY);
    test3 = new DateTime(date1, weather1, HolidayType.IS_HOLIDAY);
    test4 = new DateTime(date1, weather2, HolidayType.NON_HOLIDAY);
    test5 = new DateTime(date2, weather1, HolidayType.NON_HOLIDAY);
  }

  @Test
  public void getLocalDateTime() {
    assertEquals(test1.getLocalDateTime(), date1);
  }

  @Test
  public void getWeather() {
    assertEquals(test1.getWeather(), weather1);
  }

  @Test
  public void getHolidayType() {
    assertEquals(test1.getHolidayType(), HolidayType.NON_HOLIDAY);
  }

  @Test
  public void testEquals() {
    assertEquals(test1, test1);
    assertEquals(test1, test2);

    assertNotEquals(test1, test3);
    assertNotEquals(test1, test4);
    assertNotEquals(test1, test5);

    assertNotEquals(test1, null);
    assertNotEquals(test1, 0.0);
  }

  @Test
  public void testHashCode() {
    assertEquals(test1.hashCode(), test2.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "DateTime{localDateTime=2020-05-01T06:12, weather=Weather{weatherType=IS_NICE,"
        + " averageTemperature=65.0}, holidayType=NON_HOLIDAY}";
    assertEquals(test1.toString(), expected);
  }
}