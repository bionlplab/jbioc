package com.pengyifan.bioc.testing;

import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

public class BioCSchemaValidatorTest {

  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";
  private static final String LOCAL_XSD = "BioC.xsd";

  @Test
  public void testIsValid() throws Exception {
    URL url = Thread.currentThread().getContextClassLoader().getSystemResource(XML_FILENAME);
    File biocFile = new File(url.getFile());
    File xsdFile = new File(LOCAL_XSD);

    BioCSchemaValidator validator = new BioCSchemaValidator();
    assertTrue(validator.isValid(biocFile, xsdFile));
  }
}