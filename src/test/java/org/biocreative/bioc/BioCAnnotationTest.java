package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

  private BioCAnnotation.Builder baseBuilder;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    baseBuilder = BioCAnnotation
        .newBuilder()
        .setID(ID)
        .addLocation(LOC_1)
        .addLocation(LOC_2)
        .setText(TEXT)
        .putInfon(KEY, VALUE);
  }

  @Test
  public void testEquals() {
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
    assertEquals(ID, base.getID());
    assertEquals(TEXT, base.getText().get());
    assertEquals(VALUE, base.getInfon(KEY).get());
    assertEquals(2, base.getLocationCount());
    assertEquals(LOC_1, base.getLocation(0));
    assertEquals(LOC_2, base.getLocation(1));
  }

  @Test
  public void testBuilder_empty() {
    thrown.expect(NullPointerException.class);
    BioCAnnotation.newBuilder().build();
  }

  @Test
  public void testBuilder_nullID() {
    thrown.expect(NullPointerException.class);
    baseBuilder.setID(null);
  }

  @Test
  public void testBuilder_nullLocation() {
    thrown.expect(NullPointerException.class);
    baseBuilder.addLocation(null);
  }

  @Test
  public void testBuilder_nullText() {
    thrown.expect(NullPointerException.class);
    baseBuilder.setText(null);
  }

  @Test
  public void testToBuilder() {
    BioCAnnotation expected = baseBuilder.build();
    BioCAnnotation actual = expected.toBuilder().build();
    assertEquals(expected, actual);
  }

  @Test
  public void testBuilder_clearText() {
    BioCAnnotation ann = baseBuilder.clearText().build();
    assertFalse(ann.getText().isPresent());
  }

  @Test
  public void testBuilder_clearLocations() {
    thrown.expect(IllegalArgumentException.class);
    baseBuilder.clearLocations().build();
  }

  @Test
  public void testBuilder_removeInfon() {
    BioCAnnotation ann = baseBuilder.removeInfon(KEY).build();
    assertTrue(ann.getInfons().isEmpty());
  }

  @Test
  public void testBuilder_clearInfons() {
    BioCAnnotation ann = baseBuilder.clearInfons().build();
    assertTrue(ann.getInfons().isEmpty());
  }

  @Test
  public void testBuilder_addLocation() {
    BioCAnnotation ann = baseBuilder.addLocation(
        LOC_3.getOffset(),
        LOC_3.getLength()).build();
    assertEquals(LOC_3, ann.getLocation(2));
  }
  
  @Test
  public void testGetInfon_nullKey() {
    BioCAnnotation base = baseBuilder.build();
    assertFalse(base.getInfon(null).isPresent());
  }

}
