package com.pengyifan.nlp.bioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.EqualsTester;
import com.pengyifan.nlp.bioc.BioCSentence;

public class BioCSentenceTest {

  private static final int OFFSET = 1;
  private static final String TEXT = "ABC";
  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";

  private static final int OFFSET_2 = 2;
  private static final String TEXT_2 = "DEF";
  private static final String KEY_2 = "KEY2";
  private static final String VALUE_2 = "VALUE2";

  private BioCSentence base;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    base = new BioCSentence();
    base.setOffset(OFFSET);
    base.setText(TEXT);
    base.putInfon(KEY, VALUE);
  }

  @Test
  public void test_equals() {
    BioCSentence baseCopy = new BioCSentence(base);

    BioCSentence diffOffset = new BioCSentence(base);
    diffOffset.setOffset(OFFSET_2);

    BioCSentence diffInfon = new BioCSentence(base);
    diffInfon.putInfon(KEY_2, VALUE_2);

    BioCSentence diffText = new BioCSentence(base);
    diffText.setText(TEXT_2);

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffOffset)
        .addEqualityGroup(diffText)
        .addEqualityGroup(diffInfon)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    assertEquals(OFFSET, base.getOffset());
    assertEquals(TEXT, base.getText().get());
    assertEquals(VALUE, base.getInfon(KEY).get());
    assertTrue(base.getRelations().isEmpty());
    assertTrue(base.getAnnotations().isEmpty());
  }

  @Test
  public void test_negOffset() {
    base.setOffset(-1);
    thrown.expect(IllegalArgumentException.class);
    base.getOffset();
  }

  @Test
  public void test_nullRelation() {
    thrown.expect(NullPointerException.class);
    base.addRelation(null);
  }

  @Test
  public void test_nullAnnotation() {
    thrown.expect(NullPointerException.class);
    base.addAnnotation(null);
  }

  public void test_nullText() {
    base.setText(null);
    assertFalse(base.getText().isPresent());
  }

  @Test
  public void test_getInfon_nullKey() {
    assertFalse(base.getInfon(null).isPresent());
  }

  @Test
  public void test_clearLocations() {
    base.clearAnnotations();
    assertTrue(base.getAnnotations().isEmpty());
  }

  @Test
  public void test_removeInfon() {
    base.removeInfon(KEY);
    assertFalse(base.getInfon(KEY).isPresent());
  }

  @Test
  public void test_clearInfons() {
    base.clearInfons();
    assertTrue(base.getInfons().isEmpty());
  }
}
