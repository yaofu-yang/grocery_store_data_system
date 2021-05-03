package model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Creates an instance of the VisitParameters class with the following methods: Getter methods for
 * entry distribution, duration distribution, and additional volume.
 */
public class VisitParameters {
  private double[] entryDist;
  private double[] durationDist;
  private int additionalVolume;

  /**
   * Constructs an instance of the VisitParameters class that accepts the following three
   * parameters that are essential for additional visits construction:
   * @param volume: int representing the additional volume applied from events effect.
   * @param entry: array of double types representing the entry distribution.
   * @param duration: array of double types representing the duration distribution.
   */
  public VisitParameters(int volume, double[] entry, double[] duration) {
    this.additionalVolume = volume;
    this.entryDist = entry;
    this.durationDist = duration;
  }

  /**
   * Returns a double array representing the entry distribution
   * @return a double array representing the entry distribution
   */
  public double[] getEntryDist() {
    return this.entryDist;
  }

  /**
   * Returns a double array representing the duration distribution
   * @return a double array representing the duration distribution
   */
  public double[] getDurationDist() {
    return this.durationDist;
  }

  /**
   * Returns an int representing the additional volume
   * @return an int representing the additional volume
   */
  public int getAdditionalVolume() {
    return this.additionalVolume;
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
    VisitParameters that = (VisitParameters) o;
    return this.additionalVolume == that.additionalVolume &&
        Arrays.equals(this.entryDist, that.entryDist) &&
        Arrays.equals(this.getDurationDist(), that.durationDist);
  }

  /**
   * Returns a hashCode representation of this instance.
   * @return a hashCode representation of this instance.
   */
  @Override
  public int hashCode() {
    int result = Objects.hash(this.additionalVolume);
    result = 31 * result + Arrays.hashCode(this.entryDist);
    result = 31 * result + Arrays.hashCode(this.durationDist);
    return result;
  }

  /**
   * Returns a String representation of this instance.
   * @return a String representation of this instance.
   */
  @Override
  public String toString() {
    return "VisitParameters{" +
        "entryDist=" + Arrays.toString(this.entryDist) +
        ", durationDist=" + Arrays.toString(this.durationDist) +
        ", additionalVolume=" + this.additionalVolume +
        '}';
  }
}