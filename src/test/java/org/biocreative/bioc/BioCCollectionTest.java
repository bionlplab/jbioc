package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BioCCollectionTest {

  private static final String SOURCE = "nowhere";
  private static final String DATE = "today";

  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";

  private static BioCCollection base;
  private static BioCCollection baseCopy;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    base = new BioCCollection();
    base.setSource(SOURCE);
    base.setDate(DATE);
    base.putInfon(KEY, VALUE);

    System.out.println(base);

    baseCopy = new BioCCollection();
    baseCopy.setSource(SOURCE);
    baseCopy.setDate(DATE);
    baseCopy.putInfon(KEY, VALUE);
  }

  @Test
  public void test_equals() {
    assertEquals(base, baseCopy);
  }

  @Test
  public void test_copy() {
    assertEquals(base, new BioCCollection(baseCopy));
  }

  @Test
  public void test_allFields() {
    assertEquals(base.getSource(), SOURCE);
    assertEquals(base.getInfon(KEY), VALUE);
    assertTrue(base.getDocuments().isEmpty());
  }

}
