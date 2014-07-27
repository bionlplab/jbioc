package org.biocreative.bioc.testing;

import static org.junit.Assert.*;

import org.biocreative.bioc.testing.BioCDtdValidator;
import org.junit.Before;
import org.junit.Test;

public class BioCDtdValidatorTest {

  private static BioCDtdValidator validator;

  private static final String DTD_FILENAME = "src/test/resources/xml/BioC.dtd";

  private static final String BIOC_FILENAME = "src/test/resources/xml/everything.xml";

  @Before
  public void setUp() {
    validator = new BioCDtdValidator();
  }

  @Test
  public void test_noDtd() {
    validator.doMain(new String[] { "-dtd", DTD_FILENAME });
    assertEquals(DTD_FILENAME, validator.getDtdFilename());
  }

  @Test
  public void test_help() {
    validator.doMain(new String[] { "-h" });
    assertTrue(validator.getHelp());
  }

  @Test
  public void test_arguments() {
    validator.doMain(new String[] { "-dtd", DTD_FILENAME, BIOC_FILENAME });
    assertEquals(validator.getArguments().size(), 1);
    assertEquals(validator.getArguments().get(0), BIOC_FILENAME);
  }

  @Test
  public void test_validtor() {
    validator.doMain(new String[] { "-dtd", DTD_FILENAME, BIOC_FILENAME });
  }
}
