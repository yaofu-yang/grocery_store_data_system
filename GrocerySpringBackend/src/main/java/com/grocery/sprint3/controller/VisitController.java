package com.grocery.sprint3.controller;

import com.grocery.sprint3.model.Visit;
import com.grocery.sprint3.repository.VisitRepository;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.grocery.sprint3.service.HolidayService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling certain queries for the visits collection.
 */
@RestController
@RequestMapping(path="/visits")  // localhost:8080/visits
@CrossOrigin(allowCredentials="true")
public class VisitController {

  @Qualifier("visitRepository")
  @Autowired
  private VisitRepository visitRepo;

  @Autowired
  HolidayService holidayService;

  /**
   * Postman GET command: localhost:8080/visits/all/unordered
   * Returns all visits in the collection without modifying the native ordering.
   * @return all visits in the collection without modifying the native ordering.
   */

  @GetMapping(path="/all/unordered")
  public List<Visit> getAllVisits() {
    return (List<Visit>) visitRepo.findAll();
  }

  /**
   * Postman GET command: localhost:8080/visits/all/ordered/entry
   * Returns all visits in order of entry time.
   * @return all visits in order of entry time.
   */
  @GetMapping(path="/all/ordered/entry")
  public List<Visit> findAllByEntryTimeOrder() {
    return visitRepo.findAllByOrderByEntryTime();
  }

  /**
   * Postman GET command: localhost:8080/visits/single/{visitID}
   * Returns a single visit matching the provided visitID
   * @param id - String representing the visit ID.
   * @return a single visit matching the provided visitID
   */
  @GetMapping(path="/single/{visitID}")
  public Visit getVisitByVisitID(@PathVariable("visitID") String id) {
    return visitRepo.findAllByVisitID(id);
  }

  /**
   * Postman GET command: localhost:8080/visits/partial/prefix/{idPrefix}in{start}to{end}
   * Returns a subset of visits that match the provided prefix within the provided interval.
   * @param prefix - String representing the prefix of interest (single letter)
   * @param start - String representing the starting date/time (yyyy-MM-dd HH:mm)
   * @param end - String representing the ending date/time (yyyy-MM-dd HH:mm)
   * @return a subset of visits that match the provided prefix within the provided interval.
   */
  @GetMapping(path="/partial/prefix/{idPrefix}in{start}to{end}")
  public List<Visit> getVisitByPrefix(@PathVariable("idPrefix") String prefix,
      @PathVariable("start") String start, @PathVariable("end") String end) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime entry = LocalDateTime.parse(start, formatter);
    LocalDateTime leave = LocalDateTime.parse(end, formatter);
    return visitRepo.findAllByVisitIDContainingAndEntryTimeIsBetween(prefix, entry, leave);
  }

  /**
   * Postman GET command: localhost:8080/visits/partial/entry/interval/{start}to{end}
   * Returns a subset of visits with entry times between the given interval.
   * @param start - String representing start of interval (yyyy-MM-dd HH:mm)
   * @param end - String representing end of interval (yyyy-MM-dd HH:mm)
   * @return a subset of visits with entry times between the given interval.
   */
  @GetMapping(path="partial/entry/interval/{start}to{end}")
  public List<Visit> findAllByEntryTimeIsBetween(@PathVariable("start") String start,
      @PathVariable("end") String end) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime entry = LocalDateTime.parse(start, formatter);
    LocalDateTime leave = LocalDateTime.parse(end, formatter);
    return visitRepo.findAllByEntryTimeIsBetween(entry, leave);
  }

  /**
   * Postman GET command: localhost:8080/visits/partial/leave/interval/{start}to{end}
   * Returns a subset of visits with leave times between the given interval.
   * @param start - String representing start of interval (yyyy-MM-dd HH:mm)
   * @param end - String representing end of interval (yyyy-MM-dd HH:mm)
   * @return a subset of visits with leave times between the given interval.
   */
  @GetMapping(path="partial/leave/interval/{start}to{end}")
  public List<Visit> findAllByLeaveTimeIsBetweenm(@PathVariable("start") String start,
      @PathVariable("end") String end) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime entry = LocalDateTime.parse(start, formatter);
    LocalDateTime leave = LocalDateTime.parse(end, formatter);
    return visitRepo.findAllByLeaveTimeIsBetween(entry, leave);
  }

  /**
   * Postman GET command: localhost:8080/visits/partial/duration/interval/{min}to{max}
   * Returns a subset of visits with shopping durations in between the specified interval
   * @param min - Integer representing the minimum shopping duration
   * @param max - Integer representing the maximum shopping duration
   * @return a subset of visits with shopping durations in between the specified interval
   */
  @GetMapping(path="partial/duration/interval/{min}to{max}")
  public List<Visit> findAllByDurationIsBetween(@PathVariable("min") Integer min,
      @PathVariable("max") Integer max){
    return visitRepo.findAllByDurationBetween(min, max);
  }

  /**
   * Postman GET command: localhost:8080/visits/partial/duration/LTE/{max}
   * Returns a subset of visits with shopping durations less than or equal the target time.
   * @param max - Integer representing the maximum shopping duration
   * @return a subset of visits with shopping durations less than or equal the target time.
   */
  @GetMapping(path="partial/duration/LTE/{max}")
  public List<Visit> findAllByDurationLessThanEqual(@PathVariable("max") Integer max) {
    return visitRepo.findAllByDurationLessThanEqual(max);
  }

  /**
   * Postman GET command: localhost:8080/visits/partial/duration/GTE/{min}
   * Returns a subset of visits with shopping durations greater than or equal the target time.
   * @param min - Integer representing the minimum shopping duration
   * @return a subset of visits with shopping durations greater than or equal the target time.
   */
  @GetMapping(path="partial/duration/GTE/{min}")
  public List<Visit> findAllByDurationGreaterThanEqual(@PathVariable("min") Integer min) {
    return visitRepo.findAllByDurationGreaterThanEqual(min);
  }

  /**
   *
   * @param data - String containing the visit data
   * @return a String indicating successful/unsuccessful addition of visit.
   */
  @PostMapping(path="/add/single")
  public String saveOrUpdateVisit(@RequestBody String data) {

    try {
      JSONObject json = new JSONObject(data);
      String visitID = json.getString("visitID");
      String entryTime = json.getString("entryTime");
      String leaveTime = json.getString("leaveTime");
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'");
      LocalDateTime et = LocalDateTime.parse(entryTime, formatter);
      LocalDateTime lt = LocalDateTime.parse(leaveTime, formatter);
      int duration = (int) Duration.between(et, lt).toMinutes();
      DayOfWeek dayOfWeek = et.getDayOfWeek();

      String holiday = holidayService.CheckHoliday(et.toLocalDate());

      System.out.println(et.toString());
      System.out.println(lt.toString());
      System.out.println(duration);
      System.out.println(holiday);
      System.out.println(dayOfWeek);
      System.out.print("\n");

      visitRepo.save(new Visit(visitID, et, lt, duration, holiday, dayOfWeek));

    } catch (JSONException e) {
      e.printStackTrace();
      return "json parsing error";
    }

    return "Visit added successfully";
  }
}
