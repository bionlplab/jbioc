package com.pengyifan.bioc.io;

import static com.pengyifan.bioc.testing.BioCAssert.assertDtdValid;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.junit.Test;

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
}
