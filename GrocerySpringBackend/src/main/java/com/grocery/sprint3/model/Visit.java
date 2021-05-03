package com.grocery.sprint3.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.bson.types.ObjectId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * Creates an instance of the Visit class which contains a single method to generate additional
 * visit information. Contains two overloaded constructors that either take no parameters, or take
 * all four parameters corresponding to its internal attributes.
 */
@Document(collection = "Visits")
public class Visit {

  @Id
  private ObjectId id = new ObjectId();
  private String visitID;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime entryTime;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime leaveTime;

  private Integer duration;
  private String holiday;
  private DayOfWeek dayOfWeek;

  /**
   * Overloaded constructor that takes the following three parameters to create a visit without id.
   * @param entryTime - LocalDateTime instance representing the entry information.
   * @param leaveTime - LocalDateTime instance representing the leave information.
   * @param duration - Integer representing the duration of visit.
   * @param holiday - String representing holiday information.
   * @param dayOfWeek - DayOfWeek instance representing day of the week.
   */
  public Visit(LocalDateTime entryTime, LocalDateTime leaveTime, Integer duration, String holiday,
               DayOfWeek dayOfWeek) {
    this.entryTime = entryTime;
    this.leaveTime = leaveTime;
    this.duration = duration;
    this.holiday = holiday;
    this.dayOfWeek = dayOfWeek;
  }

  /**
   * Overloaded constructor that takes the following four parameters to create a complete visit.
   * @param visitID - String representing the unique visit.
   * @param entryTime - LocalDateTime instance representing the entry information.
   * @param leaveTime - LocalDateTime instance representing the leave information.
   * @param duration - Integer representing the duration of visit.
   * @param holiday - String representing holiday information.
   * @param dayOfWeek - DayOfWeek instance representing day of the week.
   */
  public Visit(String visitID, LocalDateTime entryTime, LocalDateTime leaveTime, Integer duration, String holiday,
               DayOfWeek dayOfWeek) {
    this.visitID = visitID;
    this.entryTime = entryTime;
    this.leaveTime = leaveTime;
    this.duration = duration;
    this.holiday = holiday;
    this.dayOfWeek = dayOfWeek;
  }

  /**
   * Overloaded constructor that does not accept any parameters and relies on setters to update
   * internal attributes.
   */
  public Visit() {}

  /**
   * Returns an ObjectId representing the actual object ID
   * @return an ObjectId representing the actual object ID
   */
  public ObjectId getId() {
    return id;
  }

  /**
   * Given an ObjectId instance, sets the internal attribute to new value.
   * @param id - new ObjectId instance.
   */
  public void setId(ObjectId id) {
    this.id = id;
  }

  /**
   * Returns a String representing the unique visit.
   * @return a String representing the unique visit.
   */
  public String getVisitID() {
    return visitID;
  }

  /**
   * Given a String visitID, sets the internal attribute to this value.
   * @param visitID - String representing the new visitID.
   */
  public void setVisitID(String visitID) {
    this.visitID = visitID;
  }

  /**
   * Returns a LocalDateTime instance representing the entry time information.
   * @return a LocalDateTime instance representing the entry time information.
   */
  public LocalDateTime getEntryTime() {
    return this.entryTime;
  }

  /**
   * Sets the new entry time to the given parameter
   * @param entryTime - LocalDateTime instance representing new entry time information.
   */
  public void setEntryTime(LocalDateTime entryTime) {
    this.entryTime = entryTime;
  }


  /**
   * Returns a LocalDateTime instance representing the leave information.
   * @return a LocalDateTime instance representing the leave information.
   */
  public LocalDateTime getLeaveTime() {
    return this.leaveTime;
  }


  /**
   * Sets the new LeaveTime to the given parameter
   * @param leaveTime - LocalDateTime instance representing leave time information.
   */
  public void setLeaveTime(LocalDateTime leaveTime) {
    this.leaveTime = leaveTime;
  }

  /**
   * Returns an Integer representation the visit duration.
   * @return an Integer representation the visit duration.
   */
  public Integer getDuration() {
    return this.duration;
  }

  /**
   * Sets the duration attribute to given parameter
   * @param duration - Integer representing new duration.
   */
  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  /**
   * Returns a String representing the holiday information.
   * @return a String representing the holiday information.
   */
  public String getHoliday() {
    return holiday;
  }

  /**
   * Given a String, sets the holiday information attribute to new value.
   * @param holiday - String representing new holiday information.
   */
  public void setHoliday(String holiday) {
    this.holiday = holiday;
  }

  /**
   * Returns a DayOfWeek instance representing the day of week.
   * @return a DayOfWeek instance  representing the day of week.
   */
  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  /**
   * Given a DayOfWeek instance, sets the dayOfWeek attribute to the new value.
   * @param dayOfWeek - DayOfWeek instance representing the new day of week.
   */
  public void setDayOfWeek(DayOfWeek dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }
}
