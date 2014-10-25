package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.testing.EqualsTester;

public class BioCRelationTest {

  private static final String ID = "1";

  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";

  private static final String ID_2 = "2";

  private static final String KEY_2 = "KEY2";
  private static final String VALUE_2 = "VALUE2";

  private static final BioCNode NODE_1 = BioCNode.newBuilder()
      .setRefid("1")
      .setRole("x")
      .build();
  private static final BioCNode NODE_2 = BioCNode.newBuilder()
      .setRefid("2")
      .setRole("y")
      .build();
  private static final BioCNode NODE_3 = BioCNode.newBuilder()
      .setRefid("3")
      .setRole("z")
      .build();

  private BioCRelation.Builder baseBuilder;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    baseBuilder = BioCRelation.newBuilder()
        .setID(ID)
        .putInfon(KEY, VALUE)
        .addNode(NODE_1)
        .addNode(NODE_2);
  }

  @Test
  public void testEquals() {
    BioCRelation base = baseBuilder.build();
    BioCRelation baseCopy = baseBuilder.build();
    BioCRelation diffId = baseBuilder.setID(ID_2).build();
    BioCRelation diffInfon = baseBuilder.putInfon(KEY_2, VALUE_2).build();
    BioCRelation diffNode = baseBuilder.addNode(NODE_3).build();

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffId)
        .addEqualityGroup(diffNode)
        .addEqualityGroup(diffInfon)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    BioCRelation base = baseBuilder.build();

    assertEquals(ID, base.getID());
    assertEquals(VALUE, base.getInfon(KEY).get());
    assertEquals(2, base.getNodeCount());
    assertEquals(NODE_1, base.getNode(0));
    assertEquals(NODE_2, base.getNode(1));
  }

  @Test
  public void testBuilder_empty() {
    thrown.expect(NullPointerException.class);
    BioCRelation.newBuilder().build();
  }

  @Test
  public void testBuilder_nullID() {
    thrown.expect(NullPointerException.class);
    baseBuilder.setID(null);
  }

  @Test
  public void testBuilder_nullNode() {
    thrown.expect(NullPointerException.class);
    baseBuilder.addNode(null);
  }

  @Test
  public void testToBuilder() {
    BioCRelation expected = baseBuilder.build();
    BioCRelation actual = expected.toBuilder().build();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetInfon_nullKey() {
    BioCRelation base = baseBuilder.build();
    assertFalse(base.getInfon(null).isPresent());
  }

  @Test
  public void testBuilder_clearNodes() {
    thrown.expect(IllegalArgumentException.class);
    baseBuilder.clearNodes().build();
  }

  @Test
  public void testBuilder_removeInfon() {
    BioCRelation rel = baseBuilder.removeInfon(KEY).build();
    assertTrue(rel.getInfons().isEmpty());
  }

  @Test
  public void testBuilder_clearInfons() {
    BioCRelation rel = baseBuilder.clearInfons().build();
    assertTrue(rel.getInfons().isEmpty());
  }

  @Test
  public void testBuilder_addLocation() {
    BioCRelation rel = baseBuilder.addNode(
        NODE_3.getRefid(),
        NODE_3.getRole()).build();
    assertEquals(NODE_3, rel.getNode(2));
  }
}
