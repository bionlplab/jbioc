package com.pengyifan.bioc.testing;

import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class BioCDtdValidatorTest {

  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";
  private static final String LOCAL_DTD = "BioC.dtd";

  @Test
  public void testIsValid() throws Exception {
    URL url = Thread.currentThread().getContextClassLoader().getSystemResource(XML_FILENAME);
    File biocFile = new File(url.getFile());
    File dtdFile = new File(LOCAL_DTD);

    BioCDtdValidator validator = new BioCDtdValidator();
    assertTrue(validator.isValid(biocFile, dtdFile));
  }
}