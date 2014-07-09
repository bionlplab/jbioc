package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BioCSentenceTest {

  private static final int OFFSET = 1;
  private static final String TEXT = "ABC";

  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";

  // private static final BioCAnnotation ANN_1 = new BioCAnnotation(0, 1);
  // private static final BioCAnnotation ANN_2 = new BioCAnnotation(1, 2);

  private static BioCSentence base;
  private static BioCSentence baseCopy;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    base = new BioCSentence();
    base.setOffset(OFFSET);
    base.setText(TEXT);
    base.putInfon(KEY, VALUE);

    System.out.println(base);

    baseCopy = new BioCSentence();
    baseCopy.setOffset(OFFSET);
    baseCopy.setText(TEXT);
    baseCopy.putInfon(KEY, VALUE);
  }

  @Test
  public void test_equals() {
    assertEquals(base, baseCopy);
  }

  @Test
  public void test_copy() {
    assertEquals(base, new BioCSentence(baseCopy));
  }

  @Test
  public void test_allFields() {
    assertEquals(base.getOffset(), OFFSET);
    assertEquals(base.getText(), TEXT);
    assertEquals(base.getInfon(KEY), VALUE);
    assertTrue(base.getRelations().isEmpty());
    assertTrue(base.getAnnotations().isEmpty());
  }

  @Test
  public void test_negOffset() {
    base = new BioCSentence();
    thrown.expect(IllegalArgumentException.class);
    base.setOffset(-1);
  }
}
