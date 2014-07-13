package org.biocreative.bioc.io;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

import org.biocreative.bioc.io.standard.JdkStrategy;
import org.biocreative.bioc.io.woodstox.WoodstoxStrategy;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BioCFactoryTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static final BioCXMLStrategy WOODSTOX = new WoodstoxStrategy();
  private static final BioCXMLStrategy STANDARD = new JdkStrategy();

  @SuppressWarnings("deprecation")
  @Test
  public void test_illegalString() {
    thrown.expect(IllegalArgumentException.class);
    BioCFactory.newFactory("");
  }

  @Test
  public void test_sucess() {
    BioCFactory factory = BioCFactory.newFactory(WOODSTOX);
    assertThat(factory.getStrategy(), instanceOf(WoodstoxStrategy.class));

    factory = BioCFactory.newFactory(STANDARD);
    assertThat(factory.getStrategy(), instanceOf(JdkStrategy.class));

    factory = BioCFactory.newFactory(new WoodstoxStrategy());
    assertThat(factory.getStrategy(), instanceOf(WoodstoxStrategy.class));

    factory = BioCFactory.newFactory(new JdkStrategy());
    assertThat(factory.getStrategy(), instanceOf(JdkStrategy.class));
  }
}
