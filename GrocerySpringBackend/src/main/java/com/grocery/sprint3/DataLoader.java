package com.grocery.sprint3;

import com.grocery.sprint3.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class DataLoader implements ApplicationRunner {

    private VisitRepository visitRepository;

    @Autowired
    public DataLoader(@Qualifier("visitRepository") VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public void run(ApplicationArguments args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

//        visitRepository.deleteAll();

//        visitRepository.save(new Visit(
//                "1",
//                LocalDateTime.parse("2020-07-16T09:10:00Z", formatter),
//                LocalDateTime.parse("2020-07-16T09:20:00Z", formatter),
//                10,
//                "non-holiday",
//                DayOfWeek.THURSDAY));
//        visitRepository.save(new Visit(
//                "2",
//                LocalDateTime.parse("2020-07-17T19:10:00Z", formatter),
//                LocalDateTime.parse("2020-07-17T19:30:00Z", formatter),
//                20,
//                "non-holiday",
//                DayOfWeek.FRIDAY));
//        visitRepository.save(new Visit(
//                "3",
//                LocalDateTime.parse("2020-07-18T23:10:00Z", formatter),
//                LocalDateTime.parse("2020-07-18T23:40:00Z", formatter),
//                30,
//                "non-holiday",
//                DayOfWeek.SATURDAY));
//        visitRepository.save(new Visit(
//                "4",
//                LocalDateTime.parse("2020-02-01T12:10:00Z", formatter),
//                LocalDateTime.parse("2020-02-01T12:30:00Z", formatter),
//                20,
//                "non-holiday",
//                DayOfWeek.SATURDAY));
//        visitRepository.save(new Visit(
//                "5",
//                LocalDateTime.parse("2020-02-02T13:10:00Z", formatter),
//                LocalDateTime.parse("2020-02-02T13:40:00Z", formatter),
//                30,
//                "non-holiday",
//                DayOfWeek.SUNDAY));
    }
}
