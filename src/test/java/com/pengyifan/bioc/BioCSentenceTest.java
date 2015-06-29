package com.pengyifan.bioc;

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

  private static final BioCAnnotation ANN_1 = createAnnotation("a1");
  private static final BioCAnnotation ANN_2 = createAnnotation("a2");

  private static final BioCRelation REL_1 = createRelation("r1");
  private static final BioCRelation REL_2 = createRelation("r2");

  private BioCSentence base;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    base = new BioCSentence();
    base.setOffset(OFFSET);
    base.setText(TEXT);
    base.putInfon(KEY, VALUE);
    base.addAnnotation(ANN_1);
    base.addRelation(REL_1);
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

    BioCSentence diffAnn = new BioCSentence(base);
    diffAnn.addAnnotation(ANN_2);

    BioCSentence diffRel = new BioCSentence(base);
    diffRel.addRelation(REL_2);

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffOffset)
        .addEqualityGroup(diffText)
        .addEqualityGroup(diffInfon)
        .addEqualityGroup(diffAnn)
        .addEqualityGroup(diffRel)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    assertEquals(OFFSET, base.getOffset());
    assertEquals(TEXT, base.getText().get());
    assertEquals(VALUE, base.getInfon(KEY).get());
    assertEquals(ANN_1, base.getAnnotation(ANN_1.getID()).get());
    assertEquals(REL_1, base.getRelation(REL_1.getID()).get());
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

  @Test
  public void test_addAnnotation() {
    base.addAnnotation(ANN_2);
    assertEquals(ANN_2, base.getAnnotation(ANN_2.getID()).get());
  }

  @Test
  public void test_addRelation() {
    base.addRelation(REL_2);
    assertEquals(REL_2, base.getRelation(REL_2.getID()).get());
  }
  
  @Test
  public void test_duplicatedAnnotation() {
    thrown.expect(IllegalArgumentException.class);
    base.addAnnotation(ANN_1);
  }

  @Test
  public void test_duplicatedRelation() {
    thrown.expect(IllegalArgumentException.class);
    base.addRelation(REL_1);
  }

  @Test
  public void test_clearAnnotations() {
    base.clearAnnotations();
    assertTrue(base.getAnnotations().isEmpty());
  }

  @Test
  public void test_clearRelations() {
    base.clearRelations();
    assertTrue(base.getRelations().isEmpty());
  }

  private static BioCAnnotation createAnnotation(String id) {
    BioCAnnotation ann = new BioCAnnotation();
    ann.setID(id);
    return ann;
  }

  private static BioCRelation createRelation(String id) {
    BioCRelation rel = new BioCRelation();
    rel.setID(id);
    return rel;
  }
}
