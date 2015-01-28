package com.pengyifan.nlp.bioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.EqualsTester;
import com.pengyifan.nlp.bioc.BioCCollection;

public class BioCCollectionTest {

  private static final String SOURCE = "nowhere";
  private static final String DATE = "today";
  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";

  private static final String SOURCE_2 = "somewhere";
  private static final String DATE_2 = "tmw";
  private static final String KEY_2 = "KEY2";
  private static final String VALUE_2 = "VALUE2";

  private BioCCollection base;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    base = new BioCCollection();
    base.setKey(KEY);
    base.setSource(SOURCE);
    base.setDate(DATE);
    base.putInfon(KEY, VALUE);
  }

  @Test
  public void test_equals() {
    BioCCollection baseCopy = new BioCCollection(base);

    BioCCollection diffSource = new BioCCollection(base);
    diffSource.setSource(SOURCE_2);

    BioCCollection diffInfon = new BioCCollection(base);
    diffInfon.putInfon(KEY_2, VALUE_2);

    BioCCollection diffDate = new BioCCollection(base);
    diffDate.setDate(DATE_2);

    BioCCollection diffKey = new BioCCollection(base);
    diffKey.setKey(KEY_2);

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffSource)
        .addEqualityGroup(diffDate)
        .addEqualityGroup(diffKey)
        .addEqualityGroup(diffInfon)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    assertEquals(base.getSource(), SOURCE);
    assertEquals(base.getInfon(KEY).get(), VALUE);
    assertEquals(base.getKey(), KEY);
    assertTrue(base.getDocuments().isEmpty());
  }

  @Test
  public void test_nullSource() {
    base.setSource(null);
    thrown.expect(NullPointerException.class);
    base.getSource();
  }

  @Test
  public void test_nullDate() {
    base.setDate(null);
    thrown.expect(NullPointerException.class);
    base.getDate();
  }

  @Test
  public void test_nullKey() {
    base.setKey(null);
    thrown.expect(NullPointerException.class);
    base.getKey();
  }

  @Test
  public void test_getInfon_nullKey() {
    assertFalse(base.getInfon(null).isPresent());
  }

  @Test
  public void test_nullDocument() {
    thrown.expect(NullPointerException.class);
    base.addDocument(null);
  }
}
