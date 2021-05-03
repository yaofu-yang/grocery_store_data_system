package model.utility;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RandomGeneratorTest {

    @Test
    void generateEntryDataTest() {
        double[] visitDist = new double[]{0.25, 0.25, 0.25, 0.25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] hash = new int[visitDist.length];
        for(int i=0; i < 10000; i++) {
            LocalDateTime ldt = RandomGenerator.generateEntryData(1, visitDist);
            hash[ldt.getHour() - 6] = 1;
        }
        assertArrayEquals(hash, new int[]{1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    }

    @Test
    void generateDurationTest() {
        double[] dist = new double[]{0.25, 0, 0.25, 0, 0.25, 0.25};
        int[] hash = new int[dist.length];
        for(int i=0; i < 10000; i++) {
            int time = RandomGenerator.generateDuration(dist);
            if(time >= 6 && time <= 20)
                hash[0] = 1;
            else if(time >= 21 && time <= 30)
                hash[1] = 1;
            else if(time >= 31 && time <= 40)
                hash[2] = 1;
            else if(time >= 41 && time <= 50)
                hash[3] = 1;
            else if(time >= 51 && time <= 60)
                hash[4] = 1;
            else if(time >= 61 && time <= 75)
                hash[5] = 1;
        }
        assertArrayEquals(hash, new int[]{1, 0, 1, 0, 1, 1});
    }
}
