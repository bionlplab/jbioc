package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.EqualsTester;

public class BioCSentenceTest {

  private static final int OFFSET = 1;
  private static final String TEXT = "ABC";
  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";

  private static final int OFFSET_2 = 2;
  private static final String TEXT_2 = "DEF";
  private static final String KEY_2 = "KEY2";
  private static final String VALUE_2 = "VALUE2";
  
  private BioCSentence.Builder baseBuilder;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    baseBuilder = BioCSentence.newBuilder()
        .setOffset(OFFSET)
        .setText(TEXT)
        .putInfon(KEY, VALUE);
  }

  @Test
  public void testEquals() {
    BioCSentence base = baseBuilder.build();
    BioCSentence baseCopy = baseBuilder.build();
    BioCSentence diffOffset = baseBuilder.setOffset(OFFSET_2).build();
    BioCSentence diffInfon = baseBuilder.putInfon(KEY_2, VALUE_2).build();
    BioCSentence diffText = baseBuilder.setText(TEXT_2).build();

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffOffset)
        .addEqualityGroup(diffText)
        .addEqualityGroup(diffInfon)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    BioCSentence base = baseBuilder.build();

    assertEquals(OFFSET, base.getOffset());
    assertEquals(TEXT, base.getText().get());
    assertEquals(VALUE, base.getInfon(KEY).get());
    assertTrue(base.getRelations().isEmpty());
    assertTrue(base.getAnnotations().isEmpty());
  }

  @Test
  public void testBuilder_negOffset() {
    thrown.expect(IllegalArgumentException.class);
    baseBuilder.setOffset(-1);
  }

  @Test
  public void testBuilder_empty() {
    thrown.expect(IllegalArgumentException.class);
    BioCSentence.newBuilder().build();
  }

  @Test
  public void testBuilder_nullRelation() {
    thrown.expect(NullPointerException.class);
    baseBuilder.addRelation(null);
  }

  @Test
  public void testBuilder_nullAnnotation() {
    thrown.expect(NullPointerException.class);
    baseBuilder.addAnnotation(null);
  }

  @Test
  public void testBuilder_nullText() {
    thrown.expect(NullPointerException.class);
    baseBuilder.setText(null);
  }

  @Test
  public void testToBuilder() {
    BioCSentence expected = baseBuilder.build();
    BioCSentence actual = expected.toBuilder().build();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetInfon_nullKey() {
    BioCSentence base = baseBuilder.build();
    assertFalse(base.getInfon(null).isPresent());
  }

  @Test
  public void testBuilder_clearText() {
    BioCSentence sen = baseBuilder.clearText().build();
    assertFalse(sen.getText().isPresent());
  }

  @Test
  public void testBuilder_clearLocations() {
    BioCSentence sen = baseBuilder.clearAnnotations().build();
    assertTrue(sen.getAnnotations().isEmpty());
  }

  @Test
  public void testBuilder_removeInfon() {
    BioCSentence sen = baseBuilder.removeInfon(KEY).build();
    assertTrue(sen.getInfons().isEmpty());
  }

  @Test
  public void testBuilder_clearInfons() {
    BioCSentence sen = baseBuilder.clearInfons().build();
    assertTrue(sen.getInfons().isEmpty());
  }
}
