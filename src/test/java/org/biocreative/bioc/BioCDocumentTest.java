package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BioCDocumentTest {

  private static final String ID = "1";

  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";

  private static BioCDocument base;
  private static BioCDocument baseCopy;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    base = new BioCDocument();
    base.setID(ID);
    base.putInfon(KEY, VALUE);

    System.out.println(base);

    baseCopy = new BioCDocument();
    baseCopy.setID(ID);
    baseCopy.putInfon(KEY, VALUE);
  }

  @Test
  public void test_equals() {
    assertEquals(base, baseCopy);
  }

  @Test
  public void test_copy() {
    assertEquals(base, new BioCDocument(baseCopy));
  }

  @Test
  public void test_allFields() {
    assertEquals(base.getID(), ID);
    assertEquals(base.getInfon(KEY), VALUE);
    assertTrue(base.getRelations().isEmpty());
    assertTrue(base.getPassages().isEmpty());
  }

}
