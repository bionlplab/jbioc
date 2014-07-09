package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class BioCNodeTest {
  
  private static final String REFID = "1";
  private static final String ROLE = "role1";
  
  private static final BioCNode BASE =  new BioCNode(REFID, ROLE);
  private static final BioCNode BASE_COPY =  new BioCNode(REFID, ROLE);
  
  @Before
  public void setUp() {
    System.out.println(BASE);
  }
  
  
  @Test
  public void test_equals() {
    assertEquals(BASE, BASE_COPY);
  }
  
  @Test
  public void test_copy() {
    assertEquals(BASE, new BioCNode(BASE));
  }

  @Test
  public void test_allFields() {
    assertEquals(BASE.getRefid(), REFID);
    assertEquals(BASE.getRole(), ROLE);
  }
}
