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

  private static final BioCNode NODE_1 = BioCNode.newBuilder()
      .setRefid("1")
      .setRole("x")
      .build();
  private static final BioCNode NODE_2 = BioCNode.newBuilder()
      .setRefid("2")
      .setRole("y")
      .build();

  private static final BioCRelation.Builder BUILDER = BioCRelation.newBuilder()
      .setID(ID)
      .putInfon(KEY, VALUE)
      .addNode(NODE_1)
      .addNode(NODE_2);

  @Rule
  public ExpectedException thrown = ExpectedException.none();

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

    BioCRelation.Builder builder = BioCRelation.newBuilder()
        .setID(ID)
        .putInfon(KEY, VALUE)
        .addNode(NODE_1)
        .addNode(NODE_2);

    BioCRelation base = BioCRelation.newBuilder()
        .setID(ID)
        .putInfon(KEY, VALUE)
        .addNode(NODE_1)
        .addNode(NODE_2)
        .build();

    assertEquals(base.getID(), ID);
    assertEquals(base.getInfon(KEY), VALUE);
    assertEquals(base.getNodes().size(), 2);
    assertEquals(base.getNodes().get(0), NODE_1);
    assertEquals(base.getNodes().get(1), NODE_2);
  }
}
