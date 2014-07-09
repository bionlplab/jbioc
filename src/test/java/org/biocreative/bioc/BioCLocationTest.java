package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BioCLocationTest {

  private static final int LENGTH = 1;
  private static final int OFFSET = 2;

  private static final BioCLocation BASE = new BioCLocation(OFFSET, LENGTH);
  private static final BioCLocation BASE_COPY = new BioCLocation(OFFSET, LENGTH);

  @Rule
  public ExpectedException thrown = ExpectedException.none();
  
  @Before
  public void setUp() {
    System.out.println(BASE);
  }

  @Test
  public void test_equals() {
    assertEquals(BASE, BASE_COPY);
  }
  
  @Test
  public void test_copy() {
    assertEquals(BASE, new BioCLocation(BASE));
  }

  @Test
  public void test_allFields() {
    assertEquals(BASE.getLength(), LENGTH);
    assertEquals(BASE.getOffset(), OFFSET);
  }

  @Test
  public void test_negLength() {
    thrown.expect(IllegalArgumentException.class);
    new BioCLocation(OFFSET, -1);
  }
  
  @Test
  public void test_negOffset() {
    thrown.expect(IllegalArgumentException.class);
    new BioCLocation(-1, LENGTH);
    new BioCLocation(0, LENGTH);
  }
}
