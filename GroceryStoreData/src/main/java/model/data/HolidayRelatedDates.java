package model.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import model.HolidayType;

/**
 * Creates an instance of the class HolidayRelatedDates which contains a single method,
 * getHolidaysByYear(int), which accepts the target year and returns a collection of all of the
 * federal holidays that year.
 * Holiday data information has a pre-specified start year.
 */
public class HolidayRelatedDates {
  private static final int START_YEAR = 2019;
  private HashMap<Integer, HashMap<HolidayType, List<LocalDate>>> holidayMap;

  /**
   * Constructs an instance of the HolidayRelatedDates containing a collection of federal holidays
   * from a pre-determined start year (currently 2016) through 2020.
   */
  public HolidayRelatedDates() {
    this.holidayMap = this.loadMap(START_YEAR);
  }

  /**
   * Given an int representing the target year, returns a HashMap with key: HolidayType and
   * value: List of LocalDate objects.
   * @param year - int representing the target year of holiday data.
   * @return a HashMap with key: HolidayType and value: List of LocalDate objects.
   */
  public HashMap<HolidayType, List<LocalDate>> getHolidaysByYear(int year) {
    return this.holidayMap.get(year);
  }

  /**
   * Helper function.
   * Given an int representing the specified start year of holiday data to load, return a Hashmap
   * with key: Integer representing the year and
   * value: HashMap with key: HolidayType and value: List of LocalDate objects.
   * @param startYear - int representing the specified start year of holiday data to load.
   * @return a HashMap with key: Integer and value: HashMap of key: HolidayType, HashMap: List of
   * LocalDate objects.
   */
  private HashMap<Integer, HashMap<HolidayType, List<LocalDate>>> loadMap(int startYear) {
      HashMap<Integer, HashMap<HolidayType, List<LocalDate>>> mapYears = new HashMap<>();
      for (List<LocalDate> holidays : this.generateHolidaysPerYear()) {
        // For each iteration, load a key: Integer mapped to value: HashMap of holiday data.
        HashMap<HolidayType, List<LocalDate>> mapYear = new HashMap<>();
        List<LocalDate> dayBeforeHolidays = this.subtractByDays(holidays, 1);
        List<LocalDate> weekToHolidays = this.subtractByDays(holidays, 7);
        mapYear.put(HolidayType.IS_HOLIDAY, holidays);
        mapYear.put(HolidayType.DAY_BEFORE_HOLIDAY, dayBeforeHolidays);
        mapYear.put(HolidayType.WEEK_TO_HOLIDAY, weekToHolidays);
        mapYears.put(startYear++, mapYear);
      }
      return mapYears;
  }

  /**
   * Returns a List of List of LocalDate objects representing the federal holiday dates per year,
   * starting from a pre-specified year (2016) to pre-specified end year (2020)
   * @return a List of List of LocalData objects representing the holiday dates per year.
   */
  private List<List<LocalDate>> generateHolidaysPerYear() {
    List<List<LocalDate>> holidaysPerYear = new ArrayList<>();
    holidaysPerYear.add(this.generate2019holidays());
    holidaysPerYear.add(this.generate2020holidays());
    return holidaysPerYear;
  }

  /**
   * Returns a List of LocalDate objects representing the federal holidays in year 2019.
   * @return a List of LocalDate objects representing the federal holidays in year 2019.
   */
  private List<LocalDate> generate2019holidays() {
    final int YEAR = 2019;
    List<LocalDate> dates = new ArrayList<>();
    dates.add(LocalDate.of(YEAR, 1, 1));  // May need to include 1/2 for Holiday Observance
    dates.add(LocalDate.of(YEAR, 1, 21));  // MLK
    dates.add(LocalDate.of(YEAR, 2, 18));  // President's Day
    dates.add(LocalDate.of(YEAR, 5, 27));  // Memorial Day
    dates.add(LocalDate.of(YEAR, 7, 4));  // Independence Day
    dates.add(LocalDate.of(YEAR, 9, 2));  // Labor Day
    dates.add(LocalDate.of(YEAR, 10, 14));  // Columbus Day
    dates.add(LocalDate.of(YEAR, 11, 11));  // Veteran's. May need to include 11/12 for Observed
    dates.add(LocalDate.of(YEAR, 11, 28));  // Thanksgiving
    dates.add(LocalDate.of(YEAR, 12, 25));  // Christmas. 12/24 Christmas Eve
    return dates;
  }

  /**
   * Returns a List of LocalDate objects representing the federal holidays in year 2020.
   * @return a List of LocalDate objects representing the federal holidays in year 2020.
   */
  private List<LocalDate> generate2020holidays() {
    final int YEAR = 2020;
    List<LocalDate> dates = new ArrayList<>();
    dates.add(LocalDate.of(YEAR, 1, 1));  // May need to include 1/2 for Holiday Observance
    dates.add(LocalDate.of(YEAR, 1, 20));  // MLK
    dates.add(LocalDate.of(YEAR, 2, 17));  // President's Day
    dates.add(LocalDate.of(YEAR, 5, 25));  // Memorial Day
    dates.add(LocalDate.of(YEAR, 7, 4));  // Independence Day. 7/3 Observed.
    dates.add(LocalDate.of(YEAR, 9, 7));  // Labor Day
    dates.add(LocalDate.of(YEAR, 10, 12));  // Columbus Day
    dates.add(LocalDate.of(YEAR, 11, 11));  // Veteran's. May need to include 11/12 for Observed
    dates.add(LocalDate.of(YEAR, 11, 26));  // Thanksgiving
    dates.add(LocalDate.of(YEAR, 12, 25));  // Christmas. 12/24 Christmas Eve
    return dates;
  }

  /**
   * Returns a list of LocalDate objects modified by the following parameters:
   * @param holidays - List of LocalDate objects of federal holiday dates.
   * @param days - number of days to subtract from the holidays.
   * @return a list of LocalDate objects representing all holiday dates subtracted by the specified
   * number of days.
   */
  private List<LocalDate> subtractByDays(List<LocalDate> holidays, long days) {
    // Uses Java stream and lambda for modification.
    return holidays.stream()
        .map(holiday -> holiday.minusDays(days))
        .collect(Collectors.toList());
  }
}
