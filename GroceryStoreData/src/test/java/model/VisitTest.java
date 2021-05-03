package model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class VisitTest {
  LocalDateTime date1;
  LocalDateTime date2;
  LocalDateTime date3;
  LocalDateTime date4;
  Weather weather1;
  Weather weather2;

  DateTime entry1;
  DateTime entry2;
  DateTime leave1;
  DateTime leave2;

  Visit test1;
  Visit test2;
  Visit test3;
  Visit test4;
  Visit test5;

  @Before
  public void setUp() {
    weather1 = new Weather(WeatherType.IS_NICE, 65.0);
    weather2 = new Weather(WeatherType.IS_NEUTRAL, 65.0);

    date1 = LocalDateTime.of(2020, 5, 1, 6, 12);
    date2 = LocalDateTime.of(2020, 5, 1, 6, 20);
    date3 = LocalDateTime.of(2020, 5, 1, 6, 30);
    date4 = LocalDateTime.of(2020, 5, 1, 6, 59);

    entry1 = new DateTime(date1, weather1, HolidayType.NON_HOLIDAY);
    entry2 = new DateTime(date2, weather1, HolidayType.NON_HOLIDAY);
    leave1 = new DateTime(date3, weather1, HolidayType.NON_HOLIDAY);
    leave2 = new DateTime(date4, weather1, HolidayType.NON_HOLIDAY);

    test1 = new Visit("1", entry1, leave1, 18);
    test2 = new Visit(entry1, leave1, 18);
    test2.setVisitID("1");

    test3 = new Visit("2", entry1, leave1, 18);
    test4 = new Visit("1", entry2, leave1, 10);
    test5 = new Visit("1", entry1, leave2, 47);

  }

  @Test
  public void getVisitID() {
    assertEquals(test1.getVisitID(), "1");
  }

  @Test
  public void setVisitID() {
    test1.setVisitID("3");
    assertEquals(test1.getVisitID(), "3");
  }

  @Test
  public void getEntryTime() {
    assertEquals(test1.getEntryTime(), entry1);
  }

  @Test
  public void getLeaveTime() {
    assertEquals(test1.getLeaveTime(), leave1);
  }

  @Test
  public void getDuration() {
    assertEquals(test1.getDuration(), 18);
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
    String expected = "Visit{visitID='1', entryTime=DateTime{localDateTime=2020-05-01T06:12,"
        + " weather=Weather{weatherType=IS_NICE, averageTemperature=65.0},"
        + " holidayType=NON_HOLIDAY}, leaveTime=DateTime{localDateTime=2020-05-01T06:30,"
        + " weather=Weather{weatherType=IS_NICE, averageTemperature=65.0},"
        + " holidayType=NON_HOLIDAY}, duration=18}";
    assertEquals(test1.toString(), expected);
  }
}