package model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DayTest {
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

  Visit visit1;
  Visit visit2;

  Day test1;
  Day test2;
  Day test3;

  @Before
  public void setUp() {
    weather1 = new Weather(WeatherType.IS_NICE, 65.0);
    weather2 = new Weather(WeatherType.IS_POOR, 65.0);

    date1 = LocalDateTime.of(2020, 5, 1, 6, 12);
    date2 = LocalDateTime.of(2020, 5, 1, 6, 20);
    date3 = LocalDateTime.of(2020, 5, 1, 6, 30);
    date4 = LocalDateTime.of(2020, 5, 1, 6, 59);

    entry1 = new DateTime(date1, weather1, HolidayType.NON_HOLIDAY);
    entry2 = new DateTime(date2, weather1, HolidayType.NON_HOLIDAY);
    leave1 = new DateTime(date3, weather1, HolidayType.NON_HOLIDAY);
    leave2 = new DateTime(date4, weather1, HolidayType.NON_HOLIDAY);

    visit1 = new Visit("1", entry1, leave1, 18);
    visit2 = new Visit("1", leave1, leave2, 18);

    List<Visit> visitList = new ArrayList<>();
    visitList.add(visit1);

    test1 = new Day();
    test1.addVisit(visit1);

    test2 = new Day();
    test2.mergeVisits(visitList);

    test3 = new Day();
    test3.addVisit(visit2);
  }

  @Test
  public void addVisit() {
    Day newDay = new Day();
    newDay.addVisit(visit1);
    assertEquals(test1.getVisits(), newDay.getVisits());
  }

  @Test
  public void getVisits() {
    String expected = "[Visit{visitID='1', entryTime=DateTime{localDateTime=2020-05-01T06:12,"
        + " weather=Weather{weatherType=IS_NICE, averageTemperature=65.0},"
        + " holidayType=NON_HOLIDAY}, leaveTime=DateTime{localDateTime=2020-05-01T06:30,"
        + " weather=Weather{weatherType=IS_NICE, averageTemperature=65.0},"
        + " holidayType=NON_HOLIDAY}, duration=18}]";
    assertEquals(test1.getVisits().toString(), expected);
  }

  @Test
  public void testEquals() {
    assertEquals(test1, test1);
    assertEquals(test1, test2);

    assertNotEquals(test1, test3);
    assertNotEquals(test1, null);
    assertNotEquals(test1, 0.0);
  }

  @Test
  public void testHashCode() {
    assertEquals(test1.hashCode(), test2.hashCode());
  }

  @Test
  public void testToString() {
    // Todo: Fix expected
    String expected = "Day{visits=[Visit{visitID='1',"
        + " entryTime=DateTime{localDateTime=2020-05-01T06:12,"
        + " weather=Weather{weatherType=IS_NICE, averageTemperature=65.0},"
        + " holidayType=NON_HOLIDAY}, leaveTime=DateTime{localDateTime=2020-05-01T06:30,"
        + " weather=Weather{weatherType=IS_NICE, averageTemperature=65.0},"
        + " holidayType=NON_HOLIDAY}, duration=18}]}";
    assertEquals(test1.toString(), expected);
  }
}