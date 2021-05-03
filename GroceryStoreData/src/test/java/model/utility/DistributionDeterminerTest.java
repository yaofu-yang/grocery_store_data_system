package model.utility;

import java.lang.Math;
import model.data.Constant;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import model.HolidayType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DistributionDeterminerTest {
    // Todo: Need to fix this test since we changed the baseline volume.

    private LocalDate ld = LocalDate.of(2020, 5, 15);
    private Constant constant = new Constant();

    @Test
    void getDailyVolumeTest() {
        assertEquals(DistributionDeterminer.getDailyVolume(ld, constant), 2200);
    }

    @Test
    void getEntryTimeTest() {
        double[] hash = new double[15];
        for (int i = 0; i < 1000000; i++) {
            LocalDateTime ldt = DistributionDeterminer.getEntryTime(1, ld, constant);
            hash[ldt.getHour() - 6]++;
        }
        double[] base = constant.getEntryTimeDist().get("Weekday");
        for(int i=0; i < base.length; i++) {
            assertTrue(Math.abs(base[i] - hash[i]/1000000) <= 0.01);
        }
    }

    @Test
    void getDurationDistributionTest() {
        LocalDateTime ldt = ld.atTime(12, 1);
        assertArrayEquals(DistributionDeterminer.getDurationDistribution(ldt, constant,
            HolidayType.NON_HOLIDAY), new double[]{0.3, 0.5, 0.15, 0.05, 0, 0});
    }

}
