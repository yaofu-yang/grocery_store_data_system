package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Creates an instance of the DataTime class that contains getter methods for its three internal
 * attributes. Includes weather information and holiday information for the date/time.
 */
public class DateTime {
    private LocalDateTime localDateTime;
    private Weather weather;
    private HolidayType holidayType;

    /**
     * Constructs an instance of the DateTime class by accepting the following three parameters:
     * @param localDateTime - A LocalDateTime instance representing the date and time of interest.
     * @param weather - a Weather instance representing weather information.
     * @param holidayType - A HolidayType representing the holiday modifier.
     */
    public DateTime(LocalDateTime localDateTime, Weather weather, HolidayType holidayType) {
        this.localDateTime = localDateTime;
        this.weather = weather;
        this.holidayType = holidayType;
    }

    /**
     * Returns a LocalDateTime instance representing the date and time of interest.
     * @return a LocalDateTime instance representing the date and time of interest.
     */
    public LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }

    /**
     * Returns a Weather instance representing the weather information.
     * @return a Weather instance representing the weather information.
     */
    public Weather getWeather() {
        return this.weather;
    }

    /**
     * Returns a HolidayType representing the holiday modifier.
     * @return a HolidayType representing the holiday modifier.
     */
    public HolidayType getHolidayType() {
        return this.holidayType;
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
        DateTime dateTime = (DateTime) o;
        return Objects.equals(this.getLocalDateTime(), dateTime.getLocalDateTime()) &&
            Objects.equals(this.getWeather(), dateTime.getWeather()) &&
            this.getHolidayType() == dateTime.getHolidayType();
    }

    /**
     * Returns a hashCode representation of this instance.
     * @return a hashCode representation of this instance.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getLocalDateTime(), this.getWeather(), this.getHolidayType());
    }

    /**
     * Returns a String representation of this instance.
     * @return a String representation of this instance.
     */
    @Override
    public String toString() {
        return "DateTime{" +
            "localDateTime=" + this.localDateTime +
            ", weather=" + this.weather +
            ", holidayType=" + this.holidayType +
            '}';
    }
}
