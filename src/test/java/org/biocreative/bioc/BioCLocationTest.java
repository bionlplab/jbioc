package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.EqualsTester;

public class BioCLocationTest {

  private static final int LENGTH = 1;
  private static final int OFFSET = 2;

  private static final int LENGTH_2 = 2;
  private static final int OFFSET_2 = 3;

  private static final BioCLocation.Builder BUILDER = BioCLocation.newBuilder()
      .setOffset(OFFSET)
      .setLength(LENGTH);

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_allFields() {

    BioCLocation base = BUILDER.build();

    System.out.println(base);

    assertEquals(base.getOffset(), OFFSET);
    assertEquals(base.getLength(), LENGTH);
  }

  @Test
  public void test_equals() {
    BioCLocation base = BUILDER.build();
    BioCLocation baseCopy = BUILDER.build();

    BioCLocation diffOffset = BUILDER.setOffset(OFFSET_2).build();
    BioCLocation diffLength = BUILDER.setLength(LENGTH_2).build();

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffOffset)
        .addEqualityGroup(diffLength)
        .testEquals();
  }

  @Test
  public void test_negLength() {
    thrown.expect(IllegalArgumentException.class);
    BioCLocation.newBuilder().setLength(-1).build();
  }

  @Test
  public void test_negOffset() {
    thrown.expect(IllegalArgumentException.class);
    BioCLocation.newBuilder().setOffset(-1).build();
  }

  @Test
  public void test_empty() {
    thrown.expect(IllegalArgumentException.class);
    BioCLocation.newBuilder().build();
  }
}
