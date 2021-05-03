package model.utility;

import model.HolidayType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HolidayDeterminerTest {

    @Test
    void getHolidayInfoTest() {
        LocalDate ld = LocalDate.of(2020, 5, 25);
        assertEquals(HolidayDeterminer.getHolidayInfo(ld), HolidayType.IS_HOLIDAY);
        ld = ld.withDayOfMonth(20);
        assertEquals(HolidayDeterminer.getHolidayInfo(ld), HolidayType.WEEK_TO_HOLIDAY);
        ld = ld.withMonth(1);
        assertEquals(HolidayDeterminer.getHolidayInfo(ld), HolidayType.IS_HOLIDAY);
    }


}
