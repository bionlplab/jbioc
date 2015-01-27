package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.EqualsTester;

public class BioCDocumentTest {

  private static final String ID = "1";
  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";

  private static final String ID_2 = "2";
  private static final String KEY_2 = "KEY2";
  private static final String VALUE_2 = "VALUE2";

  private BioCDocument base;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    base = new BioCDocument();
    base.setID(ID);
    base.putInfon(KEY, VALUE);
  }

  @Test
  public void test_equals() {
    BioCDocument baseCopy = new BioCDocument(base);
    
    BioCDocument diffId = new BioCDocument(base);
    diffId.setID(ID_2);
    
    BioCDocument diffInfon = new BioCDocument(base);
    diffInfon.putInfon(KEY_2, VALUE_2);

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffId)
        .addEqualityGroup(diffInfon)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    assertEquals(base.getID(), ID);
    assertEquals(base.getInfon(KEY).get(), VALUE);
    assertTrue(base.getRelations().isEmpty());
    assertTrue(base.getPassages().isEmpty());
  }

  @Test
  public void test_nullID() {
    base.setID(null);
    thrown.expect(NullPointerException.class);
    base.getID();
  }

  @Test
  public void test_nullPassage() {
    thrown.expect(NullPointerException.class);
    base.addPassage(null);
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

  @Test
  public void testGetInfon_nullKey() {
    assertFalse(base.getInfon(null).isPresent());
  }

}
