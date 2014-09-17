package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.EqualsTester;

public class BioCLocationTest {

  private static final int LENGTH = 1;
  private static final int OFFSET = 2;

  private static final int LENGTH_2 = 2;
  private static final int OFFSET_2 = 3;

  private BioCLocation.Builder baseBuilder;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    baseBuilder = BioCLocation.newBuilder()
        .setOffset(OFFSET)
        .setLength(LENGTH);
  }

  @Test
  public void test_allFields() {
    BioCLocation base = baseBuilder.build();

    assertEquals(base.getOffset(), OFFSET);
    assertEquals(base.getLength(), LENGTH);
  }

  @Test
  public void testEquals() {
    BioCLocation base = baseBuilder.build();
    BioCLocation baseCopy = baseBuilder.build();
    BioCLocation diffOffset = baseBuilder.setOffset(OFFSET_2).build();
    BioCLocation diffLength = baseBuilder.setLength(LENGTH_2).build();

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffOffset)
        .addEqualityGroup(diffLength)
        .testEquals();
  }

  @Test
  public void testBuilder_negLength() {
    thrown.expect(IllegalArgumentException.class);
    BioCLocation.newBuilder().setLength(-1).build();
  }

  @Test
  public void testBuilder_negOffset() {
    thrown.expect(IllegalArgumentException.class);
    BioCLocation.newBuilder().setOffset(-1).build();
  }

  @Test
  public void testBuilder_empty() {
    thrown.expect(IllegalArgumentException.class);
    BioCLocation.newBuilder().build();
  }

  @Test
  public void testToBuilder() {
    BioCLocation expected = baseBuilder.build();
    BioCLocation actual = expected.toBuilder().build();
    assertEquals(expected, actual);
  }
}
