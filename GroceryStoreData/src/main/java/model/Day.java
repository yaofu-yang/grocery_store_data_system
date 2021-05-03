package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Creates an instance of the Day class which contains a public method to add a Visit instance to
 * its internal collection.
 */
public class Day {
  private List<Visit> visits;

  /**
   * Constructor for an instance of the Day class that does not accept any parameters.
   */
  public Day() {
    this.visits = new ArrayList<>();
  }

  /**
   * Adds the given Visit instance to the internal collection of visits.
   * @param visit - Visit instance to be added to collection.
   */
  public void addVisit(Visit visit) {
    this.visits.add(visit);
  }

  /**
   * Given a list of Visit objects, add all objects to internal collection of Visit instances.
   * @param visits - List of Visit objects to be added to current collection.
   */
  public void mergeVisits(List<Visit> visits) {
    if (visits != null) {
      this.visits.addAll(visits);
    }
  }

  /**
   * Returns a list of Visit objects representing all of the visits on this day.
   * @return a list of Visit objects representing all of the visits on this day.
   */
  public List<Visit> getVisits() {
    return this.visits;
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
    Day day = (Day) o;
    return Objects.equals(this.getVisits(), day.getVisits());
  }

  /**
   * Returns a hashCode representation of this instance.
   * @return a hashCode representation of this instance.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.getVisits());
  }

  /**
   * Returns a String representation of this instance.
   * @return a String representation of this instance.
   */
  @Override
  public String toString() {
    return "Day{" +
        "visits=" + this.visits +
        '}';
  }
}
