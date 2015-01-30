package com.pengyifan.bioc;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.EqualsTester;
import com.pengyifan.bioc.BioCNode;

public class BioCNodeTest {

  private static final String REFID = "1";
  private static final String ROLE = "role1";

  private static final String REFID_2 = "2";
  private static final String ROLE_2 = "role2";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_equals() {

    BioCNode base = new BioCNode(REFID, ROLE);
    BioCNode baseCopy = new BioCNode(base);
    BioCNode diffRefid = new BioCNode(REFID_2, ROLE);
    
    BioCNode diffRefid2 = new BioCNode(base);
    diffRefid2.setRefid(REFID_2);
    
    BioCNode diffRole = new BioCNode(REFID, ROLE_2);
    
    BioCNode diffRole2 = new BioCNode(base);
    diffRole2.setRole(ROLE_2);
    
    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffRefid, diffRefid2)
        .addEqualityGroup(diffRole, diffRole2)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    BioCNode base = new BioCNode(REFID, ROLE);
    assertEquals(base.getRefid(), REFID);
    assertEquals(base.getRole(), ROLE);
  }

  @Test
  public void test_nullRefid() {
    BioCNode base = new BioCNode(null, ROLE);
    thrown.expect(NullPointerException.class);
    base.getRefid();
  }

  @Test
  public void test_nullRole() {
    BioCNode base = new BioCNode(REFID, null);
    thrown.expect(NullPointerException.class);
    base.getRole();
  }
}
