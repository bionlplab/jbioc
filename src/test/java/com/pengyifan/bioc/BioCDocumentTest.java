package com.pengyifan.bioc;

import static org.hamcrest.CoreMatchers.is;
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
import com.pengyifan.bioc.BioCDocument;

public class BioCDocumentTest {

  private static final String ID = "1";
  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";

  private static final String ID_2 = "2";
  private static final String KEY_2 = "KEY2";
  private static final String VALUE_2 = "VALUE2";
  
  private static final BioCAnnotation ANN_1 = createAnnotation("a1");
  private static final BioCAnnotation ANN_2 = createAnnotation("a2");

  private static final BioCRelation REL_1 = createRelation("r1");
  private static final BioCRelation REL_2 = createRelation("r2");
  
  private static final BioCPassage PASS_1 = createPassage("ABC");
  private static final BioCPassage PASS_2 = createPassage("DEF");
  
  private BioCDocument base;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    base = new BioCDocument();
    base.setID(ID);
    base.putInfon(KEY, VALUE);
    base.addAnnotation(ANN_1);
    base.addRelation(REL_1);
    base.addPassage(PASS_1);
  }

  @Test
  public void test_equals() {
    BioCDocument baseCopy = new BioCDocument(base);
    
    BioCDocument diffId = new BioCDocument(base);
    diffId.setID(ID_2);
    
    BioCDocument diffInfon = new BioCDocument(base);
    diffInfon.putInfon(KEY_2, VALUE_2);
    
    BioCDocument diffAnn = new BioCDocument(base);
    diffAnn.addAnnotation(ANN_2);

    BioCDocument diffRel = new BioCDocument(base);
    diffRel.addRelation(REL_2);
    
    BioCDocument diffPass = new BioCDocument(base);
    diffPass.addPassage(PASS_2);

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffId)
        .addEqualityGroup(diffInfon)
        .addEqualityGroup(diffAnn)
        .addEqualityGroup(diffRel)
        .addEqualityGroup(diffPass)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    assertEquals(base.getID(), ID);
    assertEquals(base.getInfon(KEY).get(), VALUE);
    assertEquals(ANN_1, base.getAnnotation(ANN_1.getID()));
    assertEquals(REL_1, base.getRelation(REL_1.getID()));
    assertEquals(PASS_1, base.getPassage(0));
  }
  
  @Test
  public void test_removeInfon() {
    base.removeInfon(KEY);
    assertFalse(base.getInfon(KEY).isPresent());
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
  public void test_getInfon_nullKey() {
    assertFalse(base.getInfon(null).isPresent());
  }

  @Test
  public void test_addAnnotation() {
    base.addAnnotation(ANN_2);
    assertEquals(ANN_2, base.getAnnotation(ANN_2.getID()));
  }

  @Test
  public void test_addRelation() {
    base.addRelation(REL_2);
    assertEquals(REL_2, base.getRelation(REL_2.getID()));
  }
  
  @Test
  public void test_addPassage() {
    base.addPassage(PASS_2);
    assertEquals(PASS_2, base.getPassage(1));
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
  public void test_clearPassages() {
    base.clearPassages();
    assertTrue(base.getPassages().isEmpty());
  }

  @Test
  public void test_passageIterator() {
    base.addPassage(PASS_2);
    List<BioCPassage> expected = Lists.newArrayList(PASS_1, PASS_2);
    List<BioCPassage> actual = Lists.newArrayList(base.passageIterator());
    assertThat(actual, is(expected));
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
  
  private static BioCPassage createPassage(String text) {
    BioCPassage pass = new BioCPassage();
    pass.setText(text);
    return pass;
  }
}
