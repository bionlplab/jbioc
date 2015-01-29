package com.pengyifan.bioc.io;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.custommonkey.xmlunit.Validator;
import org.custommonkey.xmlunit.exceptions.ConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;

public class BioCDtdTest {

  private static final String LOCAL_DTD = "xml/BioC.dtd";

  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";

  @Test
  public void test() {
    Reader reader = new InputStreamReader(Thread.currentThread()
        .getContextClassLoader()
        .getResourceAsStream(XML_FILENAME));
    @SuppressWarnings("static-access")
    URL url = Thread.currentThread().getContextClassLoader()
        .getSystemResource(LOCAL_DTD);
    assertDtdValid(reader, url.toString());
  }
  
  private void assertDtdValid(Reader reader, String dtdFilename) {
    try {
      Validator v = new Validator(reader, dtdFilename);
      v.assertIsValid();
    } catch (ConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    }
  }
}
