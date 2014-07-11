package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.EqualsTester;

public class BioCAnnotationTest {

  private static final String ID = "1";
  private static final String TEXT = "ABC";

  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";

  private static final String ID_2 = "2";

  private static final String KEY_2 = "KEY2";
  private static final String VALUE_2 = "VALUE2";

  private static final BioCLocation LOC_1 = BioCLocation.newBuilder()
      .setOffset(0)
      .setLength(1)
      .build();

  private static final BioCLocation LOC_2 = BioCLocation.newBuilder()
      .setOffset(1)
      .setLength(2)
      .build();

  private static final BioCLocation LOC_3 = BioCLocation.newBuilder()
      .setOffset(2)
      .setLength(3)
      .build();

  private static BioCAnnotation.Builder baseBuilder;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    baseBuilder = BioCAnnotation.newBuilder()
        .setID(ID)
        .addLocation(LOC_1)
        .addLocation(LOC_2)
        .setText(TEXT)
        .putInfon(KEY, VALUE);

  }

  @Test
  public void test_equals() {
    BioCAnnotation base = baseBuilder.build();
    BioCAnnotation baseCopy = baseBuilder.build();

    BioCAnnotation diffId = baseBuilder.setID(ID_2).build();
    BioCAnnotation diffInfon = baseBuilder.putInfon(KEY_2, VALUE_2).build();
    BioCAnnotation diffLocation = baseBuilder.addLocation(LOC_3).build();

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffId)
        .addEqualityGroup(diffLocation)
        .addEqualityGroup(diffInfon)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    BioCAnnotation base = baseBuilder.build();
    System.out.println(base);

    assertEquals(base.getID(), ID);
    assertEquals(base.getText(), TEXT);
    assertEquals(base.getInfon(KEY), VALUE);
    assertEquals(base.getLocationCount(), 2);
    assertEquals(base.getLocation(0), LOC_1);
    assertEquals(base.getLocation(1), LOC_2);
  }

  @Test
  public void test_empty() {
    thrown.expect(IllegalArgumentException.class);
    BioCRelation.newBuilder().build();
  }

  @Test
  public void test_nullID() {
    thrown.expect(NullPointerException.class);
    BioCRelation.newBuilder().setID(null).build();
  }
}
