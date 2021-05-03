package com.grocery.sprint3.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@CacheConfig(cacheNames = "holidays")
public class HolidayService {

    @Autowired
    RestTemplate restTemplate;

    @Cacheable(key = "#ld")
    public String CheckHoliday(LocalDate ld) {
        int year = ld.getYear();
        int month = ld.getMonthValue();
        int day = ld.getDayOfMonth();
        String key = "36332bb3f96d2fcc2536c4b87a373f6f896ad86d";
        String url = String.format("https://calendarific.com/api/v2/holidays?&api_key=%s&country=US&year=%d&month=%d&day=%d&type=national", key, year, month, day);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.add("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        Object response = restTemplate.exchange(url, HttpMethod.GET, entity , String.class);

        Pattern pattern = Pattern.compile("\"response\":\\{.*},\\[Date");

        Matcher m = pattern.matcher(response.toString());

        if(!m.find())
            return "non-holiday";

        String found = m.group(0).replace("\"response\":", "").replace(",[Date", "");

        try {
            JSONArray holidays = new JSONObject(found).getJSONArray("holidays");
            if(holidays.length() < 1)
                return "non-holiday";
            return holidays.getJSONObject(0).getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
            return "non-holiday";
        }

    }

}
