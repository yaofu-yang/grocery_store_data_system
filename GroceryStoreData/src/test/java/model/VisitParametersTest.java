package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VisitParametersTest {
  double[] entry1 = {0.3, 0, 0, 0.4, 0.3, 0};
  double[] entry2 = {0.3, 0, 0, 0.25, 0.25, 0.2};

  double[] duration1 = {0, 0, 0.5, 0.15, 0.15, 0.1, .1, .1, 0};
  double[] duration2 = {0.1, 0.1, 0.3, 0.15, 0.15, 0.1, .1, .1, 0};

  VisitParameters test1;
  VisitParameters test2;
  VisitParameters test3;
  VisitParameters test4;
  VisitParameters test5;

  @Before
  public void setUp()  {
    test1 = new VisitParameters(1000, entry1, duration1);
    test2 = new VisitParameters(1000, entry1, duration1);
    test3 = new VisitParameters(2000, entry1, duration1);
    test4 = new VisitParameters(1000, entry2, duration1);
    test5 = new VisitParameters(1000, entry1, duration2);

  }

  @Test
  public void getEntryDist() {
    assertEquals(test1.getEntryDist(), entry1);
  }

  @Test
  public void getDurationDist() {
    assertEquals(test1.getDurationDist(), duration1);
  }

  @Test
  public void getAdditionalVolume() {
    assertEquals(test1.getAdditionalVolume(), 1000);
  }

  @Test
  public void testEquals() {
    assertEquals(test1, test1);
    assertEquals(test1, test2);

    assertNotEquals(test1, test3);
    assertNotEquals(test1, test4);
    assertNotEquals(test1, test5);
    assertNotEquals(test1, null);
    assertNotEquals(test1, 0.0);

  }

  @Test
  public void testHashCode() {
    assertEquals(test1.hashCode(), test2.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "VisitParameters{entryDist=[0.3, 0.0, 0.0, 0.4, 0.3, 0.0],"
        + " durationDist=[0.0, 0.0, 0.5, 0.15, 0.15, 0.1, 0.1, 0.1, 0.0], additionalVolume=1000}";
    assertEquals(test1.toString(), expected);
  }
}