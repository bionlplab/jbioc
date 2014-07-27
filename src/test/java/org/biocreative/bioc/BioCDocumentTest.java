package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;
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

  private static BioCDocument.Builder baseBuilder;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    baseBuilder = BioCDocument.newBuilder()
        .setID(ID)
        .putInfon(KEY, VALUE);
  }

  @Test
  public void test_equals() {
    BioCDocument base = baseBuilder.build();
    BioCDocument baseCopy = baseBuilder.build();

    BioCDocument diffId = baseBuilder.setID(ID_2).build();
    BioCDocument diffInfon = baseBuilder.putInfon(KEY_2, VALUE_2).build();

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffId)
        .addEqualityGroup(diffInfon)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    BioCDocument base = baseBuilder.build();
    
    assertEquals(base.getID(), ID);
    assertEquals(base.getInfon(KEY), VALUE);
    assertTrue(base.getRelations().isEmpty());
    assertTrue(base.getPassages().isEmpty());
  }

  @Test
  public void test_empty() {
    thrown.expect(IllegalArgumentException.class);
    BioCDocument.newBuilder().build();
  }

  @Test
  public void test_nullID() {
    thrown.expect(NullPointerException.class);
    BioCDocument.newBuilder().setID(null).build();
  }
}
