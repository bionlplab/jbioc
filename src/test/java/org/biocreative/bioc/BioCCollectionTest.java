package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.EqualsTester;

public class BioCCollectionTest {

  private static final String SOURCE = "nowhere";
  private static final String DATE = "today";
  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";

  private static final String SOURCE_2 = "somewhere";
  private static final String DATE_2 = "tmw";
  private static final String KEY_2 = "KEY2";
  private static final String VALUE_2 = "VALUE2";

  private BioCCollection.Builder baseBuilder;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    baseBuilder = BioCCollection.newBuilder()
        .setKey(KEY)
        .setSource(SOURCE)
        .setDate(DATE)
        .putInfon(KEY, VALUE);
  }
  
  @Test
  public void test_equals() {
    BioCCollection base = baseBuilder.build();
    BioCCollection baseCopy = baseBuilder.build();

    BioCCollection diffSource = baseBuilder.setSource(SOURCE_2).build();
    BioCCollection diffInfon = baseBuilder.putInfon(KEY_2, VALUE_2).build();
    BioCCollection diffDate = baseBuilder.setDate(DATE_2).build();
    BioCCollection diffKey = baseBuilder.setKey(KEY_2).build();

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
    BioCCollection base = baseBuilder.build();
    
    assertEquals(base.getSource(), SOURCE);
    assertEquals(base.getInfon(KEY), VALUE);
    assertEquals(base.getKey(), KEY);
    assertTrue(base.getDocuments().isEmpty());
  }

  @Test
  public void test_nullSource() {
    thrown.expect(NullPointerException.class);
    BioCCollection.newBuilder().setSource(null);
  }
  
  @Test
  public void test_nullDate() {
    thrown.expect(NullPointerException.class);
    BioCCollection.newBuilder().setDate(null);
  }
  
  @Test
  public void test_nullKey() {
    thrown.expect(NullPointerException.class);
    BioCCollection.newBuilder().setKey(null);
  }
  
}
