package com.grocery.sprint3.repository;

import com.grocery.sprint3.model.Visit;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * An interface that declares query methods for the visits collection that extends the
 * CrudRepository.
 */
@Component("visitRepository")
@Repository
public interface VisitRepository extends CrudRepository<Visit, String> {

  /**
   * Given a String representing a visit ID, return a Visit if it  matches that id exactly.
   * @param id - String representing a visit ID
   * @return a Visit if it  matches that id exactly.
   */
  Visit findAllByVisitID(String id);

  /**
   * Given the following parameters, return a List of Visit objects that contain the prefix and
   * have an entry time between the specified start/end time frame.
   * @param prefix - String representing the prefix to a subset of visit IDs
   * @param start - LocalDateTime representing the start of the target interval (yyyy-mm-ddThh:mm)
   * @param end - LocalDateTime representing the end of the target interval (yyyy-mm-ddThh:mm)
   * @return a List of Visit objects that contain the prefix and are between the specified
   * start/end time frame.
   */
  List<Visit> findAllByVisitIDContainingAndEntryTimeIsBetween(String prefix,
      LocalDateTime start, LocalDateTime end);

  /**
   * Given the following parameters, returns a List of Visit objects that have an entry time within
   * the specified interval.
   * @param start - LocalDateTime representing the start of the target interval (yyyy-mm-ddThh:mm)
   * @param end - LocalDateTime representing the end of the target interval (yyyy-mm-ddThh:mm)
   * @return a List of Visit objects that have an entry time within the specified interval.
   */
  List<Visit> findAllByEntryTimeIsBetween(LocalDateTime start, LocalDateTime end);

  /**
   * Given the following parameters, returns a List of Visit objects that have a leave time within
   * the specific interval.
   * @param start - LocalDateTime representing the start of the target interval (yyyy-mm-ddThh:mm)
   * @param end - LocalDateTime representing the end of the target interval (yyyy-mm-ddThh:mm)
   * @return a List of Visit objects that have a leave time within the specific interval.
   */
  List<Visit> findAllByLeaveTimeIsBetween(LocalDateTime start, LocalDateTime end);

  /**
   * Given the following parameters, returns a List of Visit instances that have a duration within
   * the provided interval.
   * @param min - Integer representing the minimum duration of target interval.
   * @param max - Integer representing the maximum duration of target interval
   * @return a List of Visit instances that have a duration within the provided interval.
   */
  List<Visit> findAllByDurationBetween(Integer min, Integer max);

  /**
   * Given the following parameter, returns a List of Visit objects that contain a duration of at
   * least the provided value.
   * @param min - Integer representing the minimum duration of search target
   * @return a List of Visit objects that contain a duration of at least the provided value.
   */
  List<Visit> findAllByDurationGreaterThanEqual(Integer min);

  /**
   * Given the following parameter, returns a List of Visit objects that contain a duration of at
   * most the provided value.
   * @param max - Integer representing the minimum duration of search target
   * @return a List of Visit objects that contain a duration of at most the provided value.
   */
  List<Visit> findAllByDurationLessThanEqual(Integer max);

  /**
   * Returns a List of Visits ordered by Entry Time
   * @return a List of Visits ordered by Entry Time
   */
  List<Visit> findAllByOrderByEntryTime();
}
