package com.pengyifan.bioc;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.Lists;
import com.google.common.testing.EqualsTester;
import com.pengyifan.bioc.BioCPassage;

public class BioCPassageTest {

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

  private static final BioCSentence SEN_1 = createSentence(TEXT);
  private static final BioCSentence SEN_2 = createSentence(TEXT_2);

  private BioCPassage base;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    base = new BioCPassage();
    base.setOffset(OFFSET);
    base.setText(TEXT);
    base.putInfon(KEY, VALUE);
    base.addAnnotation(ANN_1);
    base.addRelation(REL_1);
    base.addSentence(SEN_1);
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

    BioCPassage diffAnn = new BioCPassage(base);
    diffAnn.addAnnotation(ANN_2);

    BioCPassage diffRel = new BioCPassage(base);
    diffRel.addRelation(REL_2);

    BioCPassage diffSen = new BioCPassage(base);
    diffSen.addSentence(SEN_2);

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffOffset)
        .addEqualityGroup(diffText)
        .addEqualityGroup(diffInfon)
        .addEqualityGroup(diffAnn)
        .addEqualityGroup(diffRel)
        .addEqualityGroup(diffSen)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    assertEquals(base.getOffset(), OFFSET);
    assertEquals(base.getText().get(), TEXT);
    assertEquals(base.getInfon(KEY).get(), VALUE);
    assertEquals(ANN_1, base.getAnnotation(ANN_1.getID()).get());
    assertEquals(REL_1, base.getRelation(REL_1.getID()).get());
    assertEquals(SEN_1, base.getSentence(0));
  }

  @Test
  public void test_removeInfon() {
    base.removeInfon(KEY);
    assertFalse(base.getInfon(KEY).isPresent());
  }

  @Test
  public void test_negOffset() {
    base.setOffset(-1);
    thrown.expect(IllegalArgumentException.class);
    base.getOffset();
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
  public void test_addSentence() {
    base.addSentence(SEN_2);
    assertEquals(SEN_2, base.getSentence(1));
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

  @Test
  public void test_clearSentences() {
    base.clearSentences();
    assertTrue(base.getSentences().isEmpty());
  }

  @Test
  public void test_sentenceIterator() {
    base.addSentence(SEN_2);
    List<BioCSentence> actual = Lists.newArrayList(base.sentenceIterator());
    assertThat(actual, contains(SEN_1, SEN_2));
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

  private static BioCSentence createSentence(String text) {
    BioCSentence sen = new BioCSentence();
    sen.setOffset(0);
    sen.setText(text);
    return sen;
  }
}
