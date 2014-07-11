package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;

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
  public void test_equals() {
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

    assertEquals(base.getID(), ID);
    assertEquals(base.getInfon(KEY), VALUE);
    assertEquals(base.getNodeCount(), 2);
    assertEquals(base.getNode(0), NODE_1);
    assertEquals(base.getNode(1), NODE_2);
  }

  @Test
  public void test_empty() {
    thrown.expect(IllegalArgumentException.class);
    BioCNode.newBuilder().build();
  }
}
