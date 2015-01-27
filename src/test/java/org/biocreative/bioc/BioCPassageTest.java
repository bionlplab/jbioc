package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.EqualsTester;

public class BioCPassageTest {

  private static final int OFFSET = 1;
  private static final String TEXT = "ABC";
  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";

  private static final int OFFSET_2 = 2;
  private static final String TEXT_2 = "DEF";
  private static final String KEY_2 = "KEY2";
  private static final String VALUE_2 = "VALUE2";

  private BioCPassage base;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    base = new BioCPassage();
    base.setOffset(OFFSET);
    base.setText(TEXT);
    base.putInfon(KEY, VALUE);
  }

  @Test
  public void test_equals() {
    BioCPassage baseCopy = new BioCPassage(base);

    BioCPassage diffOffset = new BioCPassage(base);
    diffOffset.setOffset(OFFSET_2);

    BioCPassage diffInfon = new BioCPassage(base);
    diffInfon.putInfon(KEY_2, VALUE_2);

    BioCPassage diffText = new BioCPassage(base);
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
    assertEquals(base.getOffset(), OFFSET);
    assertEquals(base.getText().get(), TEXT);
    assertEquals(base.getInfon(KEY), VALUE);
    assertTrue(base.getRelations().isEmpty());
    assertTrue(base.getAnnotations().isEmpty());
    assertTrue(base.getSentences().isEmpty());
  }

  @Test
  public void testBuilder_negOffset() {
    base.setOffset(-1);
    thrown.expect(IllegalArgumentException.class);
    base.getOffset();
  }
}
