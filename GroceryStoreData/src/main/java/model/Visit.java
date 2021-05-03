package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Objects;

/**
 * Creates an instance of the Visit class which contains a single method to generate additional
 * visit information. Contains two overloaded constructors that either take no parameters, or take
 * all four parameters corresponding to its internal attributes.
 */
@Entity("Visits")
public class Visit {
  @Id
  private ObjectId id = new ObjectId();
  private String visitID;
  @Embedded
  private DateTime entryTime;
  @Embedded
  private DateTime leaveTime;
  private int duration;

  /**
   * Overloaded constructor that takes the following three parameters to create a visit without id.
   * @param entryTime - DateTime instance representing the entry information.
   * @param leaveTime - DateTime instance representing the leave information.
   * @param duration - int representing the duration of visit.
   */
  public Visit(DateTime entryTime, DateTime leaveTime, int duration) {
    this.entryTime = entryTime;
    this.leaveTime = leaveTime;
    this.duration = duration;
  }

  /**
   * Overloaded constructor that takes the following four parameters to create a complete visit.
   * @param visitID - String representing the unique visit.
   * @param entryTime - DateTime instance representing the entry information.
   * @param leaveTime - DateTime instance representing the leave information.
   * @param duration - int representing the duration of visit.
   */
  public Visit(String visitID, DateTime entryTime, DateTime leaveTime, int duration) {
    this.visitID = visitID;
    this.entryTime = entryTime;
    this.leaveTime = leaveTime;
    this.duration = duration;
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
   * Returns a DateTime instance representing the entry time information.
   * @return a DateTime instance representing the entry time information.
   */
  public DateTime getEntryTime() {
    return this.entryTime;
  }

  /**
   * Returns a DateTime instance representing the leave information.
   * @return a DateTime instance representing the leave information.
   */
  public DateTime getLeaveTime() {
    return this.leaveTime;
  }

  /**
   * Returns an Integer representation the visit duration.
   * @return an Integer representation the visit duration.
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * Returns true if this object has the same field values as the other object.
   * @param o - The other object being compared to this object.
   * @return true if this object has the same field values as the other object.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || (this.getClass() != o.getClass())) {
      return false;
    }
    Visit visit = (Visit) o;
    return Objects.equals(this.visitID, visit.visitID) &&
        Objects.equals(this.entryTime, visit.entryTime) &&
        Objects.equals(this.leaveTime, visit.leaveTime) &&
        Objects.equals(this.duration, visit.duration);
  }

  /**
   * Returns a hashCode representation of this instance.
   * @return a hashCode representation of this instance.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.visitID, this.entryTime, this.leaveTime, this.duration);
  }

  /**
   * Returns a String representation of this instance.
   * @return a String representation of this instance.
   */
  @Override
  public String toString() {
    return "Visit{" +
        "visitID='" + this.visitID + '\'' +
        ", entryTime=" + this.entryTime +
        ", leaveTime=" + this.leaveTime +
        ", duration=" + this.duration +
        '}';
  }
}
