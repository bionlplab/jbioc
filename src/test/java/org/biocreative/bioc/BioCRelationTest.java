package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BioCRelationTest {

  private static final String ID = "1";
  
  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";
  
  private static final BioCNode NODE_1 = new BioCNode("1", "x");
  private static final BioCNode NODE_2 = new BioCNode("2", "y");
  
  private static BioCRelation base;
  private static BioCRelation baseCopy;

  @Rule
  public ExpectedException thrown = ExpectedException.none();
  
  @Before
  public void setUp() {
    base =  new BioCRelation();
    base.setID(ID);
    base.addNode(NODE_1);
    base.addNode(NODE_2);
    base.putInfon(KEY, VALUE);
    
    System.out.println(base);
    
    baseCopy =  new BioCRelation();
    baseCopy.setID(ID);
    baseCopy.addNode(NODE_1);
    baseCopy.addNode(NODE_2);
    baseCopy.putInfon(KEY, VALUE);
  }

  @Test
  public void test_equals() {
    assertEquals(base, baseCopy);
  }
  
  @Test
  public void test_copy() {
    assertEquals(base, new BioCRelation(base));
  }

  @Test
  public void test_allFields() {
    assertEquals(base.getID(), ID);
    assertEquals(base.getInfon(KEY), VALUE);
    assertEquals(base.getNodes().size(), 2);
    assertEquals(base.getNodes().get(0), NODE_1);
    assertEquals(base.getNodes().get(1), NODE_2);
  }
}
