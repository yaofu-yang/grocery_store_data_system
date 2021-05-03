package model.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Weather;
import model.WeatherType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Creates an instance of the Util class that contains automatically loads weather data upon
 * instantiation. Contains a method to find weather information on a particular date/time, and
 * another method that checks if the weather type is considered "nice" by an arbitrary standard.
 */
public class WeatherParser {
    private HashMap<String, String> weatherMap;

    /**
     * Constructs an instance of the Util class without accepting any parameters.
     * Automatically loads weather information from an external database.
     */
    public WeatherParser() {

        // load weather data from json file
        JSONObject jsonObject = new JSONObject();
        JSONParser parser = new JSONParser();

        try {
            String filePath = new File("").getAbsolutePath();
            Object obj = parser.parse(new FileReader(filePath + "/src/main/java/weatherData.json"));
            jsonObject =  (JSONObject) obj;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            this.weatherMap = new ObjectMapper().readValue(jsonObject.toJSONString(), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Given a LocalDateTime instance, returns a Weather instance representing the weather
     * information at that date and time.
     * @param ldt - LocalDateTime instance representing the date and time of interest.
     * @return a Weather instance representing the weather information at that date and time.
     */
    public Weather findWeather(LocalDateTime ldt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String key = ldt.format(formatter).substring(0, 13) + ":00:00";
        String[] ans = weatherMap.get(key).split("-");
        String weatherType = ans[0];
        String temperature = ans[1];
        WeatherType weather = determineWeatherType(weatherType);
        String[] tmp = temperature.split("\\.");
        temperature = tmp[0] + "." + tmp[1].substring(0, Math.min(2, tmp[1].length()));
        return new Weather(weather, Double.valueOf(temperature));
    }

    /**
     * Given a String representing different weather conditions, returns a WeatherType representing
     * one of three weather conditions (nice, poor, neutral)
     * @param weatherType - String representing different weather conditions..
     * @return a WeatherType representing one of three weather conditions (nice, poor, neutral)
     */
    public WeatherType determineWeatherType(String weatherType) {
        switch (weatherType) {
            case "clear":
                return WeatherType.IS_NICE;
            case "drizzle":
            case "rain":
            case "hail":
                return WeatherType.IS_POOR;
        }
        return WeatherType.IS_NEUTRAL;
    }
}
