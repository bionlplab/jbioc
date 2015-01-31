package com.pengyifan.bioc.testing;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.LogMode;
import org.junit.contrib.java.lang.system.StandardErrorStreamLog;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;

public class BioCDtdValidatorTest {

  private static BioCDtdValidator validator;

  private static final String DTD_FILENAME = "xml/BioC.dtd";

  private static final String BIOC_FILENAME = "xml/everything.xml";

  @Rule
  public final StandardErrorStreamLog errLog = new StandardErrorStreamLog(
      LogMode.LOG_ONLY);
  @Rule
  public final StandardOutputStreamLog outLog = new StandardOutputStreamLog(
      LogMode.LOG_ONLY);

  @Before
  public void setUp() {
    validator = new BioCDtdValidator();
  }

  private String getAbsolutePath(String filename) {
    URL url = this.getClass().getResource("/" + filename);
    return url.getFile();
  }

  @Test
  public void test_noArgument() {
    validator.doMain(new String[] { "-dtd", getAbsolutePath(DTD_FILENAME) });
    assertEquals(getAbsolutePath(DTD_FILENAME), validator.getDtdFilename());
    assertThat(errLog.getLog(), startsWith("No argument is given"));
  }

  @Test
  public void test_help() {
    validator.doMain(new String[] { "-h" });
    assertTrue(validator.getHelp());
  }

  @Test
  public void test_arguments() {
    validator.doMain(new String[] { "-dtd", getAbsolutePath(DTD_FILENAME),
        getAbsolutePath(BIOC_FILENAME) });
    assertEquals(validator.getArguments().size(), 1);
    assertEquals(
        validator.getArguments().get(0),
        getAbsolutePath(BIOC_FILENAME));
  }

  @Test
  public void test_validtor() {
    validator.doMain(new String[] { "-dtd", getAbsolutePath(DTD_FILENAME),
        getAbsolutePath(BIOC_FILENAME) });
    assertThat(outLog.getLog(), startsWith("testing"));
  }

  @Test
  public void test_errorArgument() {
    validator.doMain(new String[] { "-xxx" });
    assertThat(errLog.getLog(), startsWith("\"-xxx\" is not a valid option"));
  }
  
  @Test
  public void test_errorDtd() {
    validator.doMain(new String[] { "-dtd", "xxx" });
    assertThat(errLog.getLog(), startsWith("cannot find the DTD file"));
  }
  
  @Test
  public void test_errorBioC() {
    validator.doMain(new String[] { "-dtd", getAbsolutePath(DTD_FILENAME), "x" });
    assertThat(errLog.getLog(), startsWith("cannot read bioc file"));
  }
}
