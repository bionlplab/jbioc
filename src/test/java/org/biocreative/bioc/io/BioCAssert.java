package org.biocreative.bioc.io;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.xmlmatchers.transform.XmlConverters.the;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.Validator;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.exceptions.ConfigurationException;
import org.xml.sax.SAXException;

public class BioCAssert {

  private static final int BUF_SIZE = 1024;
  
  public static void assertDtdValid(Reader reader, String dtdFilename) {
    try {
      Validator v = new Validator(reader, dtdFilename);
      v.assertIsValid();
    } catch (ConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    }
  }

  public static void assertXmlEquals(Reader controlReader, Reader actualReader)
      throws IOException {
    char[] cbuf = new char[BUF_SIZE];
    int size;

    StringBuilder controlXml = new StringBuilder();
    while ((size = controlReader.read(cbuf, 0, BUF_SIZE)) != -1) {
      controlXml.append(cbuf, 0, size);
    }

    StringBuilder actualXml = new StringBuilder();
    while ((size = actualReader.read(cbuf, 0, BUF_SIZE)) != -1) {
      actualXml.append(cbuf, 0, size);
    }

    assertXmlEquals(controlXml.toString(), actualXml.toString());
  }

  public static void assertXmlEquals(String controlXml, String actualXml)
      throws IOException {
    assertThat(
        the(controlXml.toString()),
        isEquivalentTo(the(actualXml.toString())));
  }

  public static void assertAndPrintEquals(Reader controlReader,
      Reader testReader)
      throws Exception {

    boolean isIgnoreWhitespace = XMLUnit.getIgnoreWhitespace();
    XMLUnit.setIgnoreWhitespace(true);
    Diff diff = new Diff(controlReader, testReader);

    try {
      assertTrue(diff.similar());
    } catch (AssertionError e) {
      DetailedDiff detDiff = new DetailedDiff(diff);
      @SuppressWarnings("rawtypes")
      List allDifferences = detDiff.getAllDifferences();
      for (Object object : allDifferences) {
        Difference difference = (Difference) object;
        System.out.println(difference);
      }
    }

    XMLUnit.setIgnoreWhitespace(isIgnoreWhitespace);
  }
}
