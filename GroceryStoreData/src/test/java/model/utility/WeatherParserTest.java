package model.utility;

import model.Weather;
import model.WeatherType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

class WeatherParserTest {

    @Test
    void findWeatherTest() {
        WeatherParser weatherParser = new WeatherParser();
        LocalDateTime ldt = LocalDateTime.of(2020, 3, 31, 14, 0);
        Weather weather = weatherParser.findWeather(ldt);
        assertEquals(weather.getWeatherType(), WeatherType.IS_NICE);
        assertEquals(weather.getAverageTemperature().intValue(), 70);
    }
}
