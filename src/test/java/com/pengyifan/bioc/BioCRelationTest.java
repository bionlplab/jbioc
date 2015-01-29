package com.pengyifan.bioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.EqualsTester;
import com.pengyifan.bioc.BioCNode;
import com.pengyifan.bioc.BioCRelation;

public class BioCRelationTest {

  private static final String ID = "1";
  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";

  private static final String ID_2 = "2";
  private static final String KEY_2 = "KEY2";
  private static final String VALUE_2 = "VALUE2";

  private static final BioCNode NODE_1 = new BioCNode("1", "x");
  private static final BioCNode NODE_2 = new BioCNode("2", "y");
  private static final BioCNode NODE_3 = new BioCNode("3", "z");

  private BioCRelation base;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    base = new BioCRelation();
    base.setID(ID);
    base.putInfon(KEY, VALUE);
    base.addNode(NODE_1);
    base.addNode(NODE_2);
  }

  @Test
  public void test_equals() {
    BioCRelation baseCopy = new BioCRelation(base);
    
    BioCRelation diffId = new BioCRelation(base);
    diffId.setID(ID_2);
    
    BioCRelation diffInfon = new BioCRelation(base);
    diffInfon.putInfon(KEY_2, VALUE_2);
    
    BioCRelation diffNode = new BioCRelation(base);
    diffNode.addNode(NODE_3);

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffId)
//        .addEqualityGroup(diffNode)
//        .addEqualityGroup(diffInfon)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    assertEquals(ID, base.getID());
    assertEquals(VALUE, base.getInfon(KEY).get());
    assertEquals(2, base.getNodeCount());
    assertTrue(base.containsNode(NODE_1));
    assertTrue(base.containsNode(NODE_2));
  }

  @Test
  public void test_nullID() {
    base.setID(null);
    thrown.expect(NullPointerException.class);
    base.getID();
  }

  @Test
  public void test_nullNode() {
    thrown.expect(NullPointerException.class);
    base.addNode(null);
  }

  @Test
  public void test_getInfon_nullKey() {
    assertFalse(base.getInfon(null).isPresent());
  }

  @Test
  public void test_clearNodes() {
    base.clearNodes();
    assertTrue(base.getNodes().isEmpty());
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
  public void test_addLocation() {
    assertFalse(base.containsNode(NODE_3));
    base.addNode(NODE_3);
    assertTrue(base.containsNode(NODE_3));
  }
}
