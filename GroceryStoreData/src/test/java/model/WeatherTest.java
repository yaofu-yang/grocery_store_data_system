package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class WeatherTest {
  Weather test1;
  Weather test2;
  Weather test3;
  Weather test4;
  Weather test5;

  @Before
  public void setUp() {
    test1 = new Weather(WeatherType.IS_NICE, 65.0);
    test2 = new Weather(WeatherType.IS_NICE, 65.0);
    test3 = new Weather(WeatherType.IS_NEUTRAL, 65.0);
    test4 = new Weather(WeatherType.IS_POOR, 65.0);
    test5 = new Weather(WeatherType.IS_NICE, 64.0);
  }

  @Test
  public void getWeatherType() {
    assertEquals(test1.getWeatherType(), WeatherType.IS_NICE);
  }

  @Test
  public void getAverageTemperature() {
    assertEquals(test1.getAverageTemperature(), Double.valueOf(65.0));
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
    // Todo: Need to fix output.
    String expected = "Weather{weatherType=IS_NICE, averageTemperature=65.0}";
    assertEquals(test1.toString(), expected);
  }
}