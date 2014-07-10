package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.EqualsTester;

public class BioCNodeTest {

  private static final String REFID = "1";
  private static final String ROLE = "role1";

  private static final String REFID_2 = "2";
  private static final String ROLE_2 = "role2";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_equals() {
    BioCNode.Builder builder = BioCNode.newBuilder()
        .setRefid(REFID)
        .setRole(ROLE);

    BioCNode base = builder.build();
    BioCNode baseCopy = builder.build();

    BioCNode diffRefid = builder.setRefid(REFID_2).build();
    BioCNode diffRole = builder.setRole(ROLE_2).build();

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffRefid)
        .addEqualityGroup(diffRole)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    BioCNode.Builder builder = BioCNode.newBuilder()
        .setRefid(REFID)
        .setRole(ROLE);

    BioCNode base = builder.build();

    System.out.println(base);

    assertEquals(base.getRefid(), REFID);
    assertEquals(base.getRole(), ROLE);
  }

  @Test
  public void test_empty() {
    thrown.expect(IllegalArgumentException.class);
    BioCNode.newBuilder().build();
  }
}
