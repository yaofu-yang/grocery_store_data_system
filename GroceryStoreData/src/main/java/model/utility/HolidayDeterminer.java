package model.utility;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import model.data.HolidayRelatedDates;
import model.HolidayType;

/**
 * A static class containing a single static method, getHolidayInfo(LocalDate) which returns a
 * self-defined enum, HolidayType based on the specified LocalDate object.
 */
public final class HolidayDeterminer {
  private HolidayDeterminer() {}

  /**
   * Returns an enum representing the type of holiday based on the provided date
   * @param date - LocalDate instance representing the date of interest.
   * @return an enum representing the type of holiday based on the provided date
   */
  public static HolidayType getHolidayInfo(LocalDate date) {
    HolidayRelatedDates dates = new HolidayRelatedDates();
    HashMap<HolidayType, List<LocalDate>> allDates = dates.getHolidaysByYear(date.getYear());

    if (isHoliday(allDates.get(HolidayType.IS_HOLIDAY), date)) {
      return HolidayType.IS_HOLIDAY;
    }

    if (isHoliday(allDates.get(HolidayType.DAY_BEFORE_HOLIDAY), date)) {
      return HolidayType.DAY_BEFORE_HOLIDAY;
    }

    if (isWeekToHoliday(allDates.get(HolidayType.WEEK_TO_HOLIDAY), date)) {
      return HolidayType.WEEK_TO_HOLIDAY;
    }

    return HolidayType.NON_HOLIDAY;
  }

  /**
   * Helper function.
   * Returns a boolean indicating whether or not the given date is a holiday/day before holiday.
   * @param localDates - List of LocalDate objects representing the holidays/days before holiday.
   * @param date - LocalDate representing a single date being compared.
   * @return a boolean indicating whether or not the given date is a holiday.
   */
  private static boolean isHoliday(List<LocalDate> localDates, LocalDate date) {
    for (LocalDate holiday : localDates) {
      if (date.equals(holiday)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns a boolean indicating whether or not the given date is within a holiday week.
   * @param localDates - List of LocalDate objects representing the holidays of that year.
   * @param date - LocalDate instance representing the date being compared.
   * @return a boolean indicating whether or not the given date is within a holiday week.
   */
  private static boolean isWeekToHoliday(List<LocalDate> localDates, LocalDate date) {
    for (LocalDate holiday : localDates) {
      int holidayTarget = holiday.getDayOfYear();
      int potentialMatch = date.getDayOfYear();
      if (potentialMatch >= holidayTarget && potentialMatch - 7 <= holidayTarget) {
        return true;
      }
    }
    return false;
  }
}
