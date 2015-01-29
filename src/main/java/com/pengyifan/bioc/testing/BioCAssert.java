package com.pengyifan.bioc.testing;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.Validator;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.exceptions.ConfigurationException;
import org.xml.sax.SAXException;

/**
 * Assertion for validating a BioC file or comparing two BioC for "equivalence"
 * and "similarity." Two documents are considered to be "equivalent" if they
 * contain the same elements in the same order. Two documents are considered to
 * be "similar" if the the content of the nodes in the documents are the same,
 * but minor differences exist.
 * 
 * @deprecated
 */
public class BioCAssert {

  private static final int BUF_SIZE = 1024;

  /**
   * Asserts that a BioC file is valid based on the given DTD file.
   * 
   * @param reader BioC file reader stream
   * @param dtdFilename the absolute URI of the BioC DTD file
   */
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

  /**
   * Asserts that a BioC file is valid based on the given DTD file. If the BioC
   * file is invalid, prints the error message.
   * 
   * @param reader BioC file reader stream
   * @param dtdFilename the absolute URI of the BioC DTD file
   */
  public static void assertAndPrintDtdValid(Reader reader, String dtdFilename) {
    try {
      Validator v = new Validator(reader, dtdFilename);
      v.assertIsValid();
    } catch (ConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (AssertionFailedError e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Asserts that two BioC files are equal. Two documents are considered to be
   * "equivalent" if they contain the same elements in the same order.
   * 
   * @param expected reader stream with expected BioC file
   * @param actual reader stream with actual BioC file
   * @throws IOException an I/O exception of some errors in the BioC file
   * @throws SAXException 
   */
  public static void assertEquivalentTo(Reader expected,
      Reader actual)
      throws IOException, SAXException {
    char[] cbuf = new char[BUF_SIZE];
    int size;

    StringBuilder controlXml = new StringBuilder();
    while ((size = expected.read(cbuf, 0, BUF_SIZE)) != -1) {
      controlXml.append(cbuf, 0, size);
    }

    StringBuilder actualXml = new StringBuilder();
    while ((size = actual.read(cbuf, 0, BUF_SIZE)) != -1) {
      actualXml.append(cbuf, 0, size);
    }

    assertEquivalentTo(controlXml.toString(), actualXml.toString());
  }

  /**
   * Asserts that two BioC files are equal. Two documents are considered to be
   * "equivalent" if they contain the same elements in the same order.
   * 
   * @param expected expected BioC file string
   * @param actual actual BioC file string
   * @throws IOException 
   * @throws SAXException 
   */
  public static void assertEquivalentTo(String expected, String actual) throws SAXException, IOException {
    Diff d = new Diff(expected, actual);
    assertTrue(d.identical());
//    assertThat(
//        the(expected.toString()),
//        isEquivalentTo(the(actual.toString())));
  }

  /**
   * Asserts that two BioC files are equal. Two documents are considered to be
   * "similar" if the the content of the nodes in the documents are the same,
   * but minor differences exist e.g. sequencing of sibling elements.
   * 
   * @param expected expected BioC file string
   * @param actual actual BioC file string
   * @throws IOException 
   * @throws SAXException 
   */
  public static void assertSimilarTo(String expected, String actual) throws SAXException, IOException {
    XMLUnit.setIgnoreWhitespace(true);
    Diff d = new Diff(expected, actual);
//    DetailedDiff dd = new DetailedDiff(d);
    assertTrue(d.similar());
//    List l = dd.getAllDifferences();
//    System.out.println(l);
////    assertThat(
////        the(expected.toString()),
////        isSimilarTo(the(actual.toString())));
  }

  /**
   * Asserts that two BioC files are equal. Two documents are considered to be
   * "similar" if the the content of the nodes in the documents are the same,
   * but minor differences exist e.g. sequencing of sibling elements.
   * 
   * @param expected reader stream with expected BioC file
   * @param actual reader stream with actual BioC file
   * @throws IOException an I/O exception of some errors in the BioC file
   * @throws SAXException 
   */
  public static void assertSimilarTo(Reader expected,
      Reader actual)
      throws IOException, SAXException {
    char[] cbuf = new char[BUF_SIZE];
    int size;

    StringBuilder controlXml = new StringBuilder();
    while ((size = expected.read(cbuf, 0, BUF_SIZE)) != -1) {
      controlXml.append(cbuf, 0, size);
    }

    StringBuilder actualXml = new StringBuilder();
    while ((size = actual.read(cbuf, 0, BUF_SIZE)) != -1) {
      actualXml.append(cbuf, 0, size);
    }

    assertSimilarTo(controlXml.toString(), actualXml.toString());
  }

  /**
   * Asserts that two BioC files are equal. If two BioC files are not equal,
   * prints the different parts.
   * 
   * @param expected reader stream with expected BioC file
   * @param actual reader stream with actual BioC file
   * @throws IOException an I/O exception of some errors in the BioC file
   * @throws SAXException SAX error or warning
   */
  public static void assertAndPrintEquals(Reader expected,
      Reader actual)
      throws SAXException, IOException {

    boolean isIgnoreWhitespace = XMLUnit.getIgnoreWhitespace();
    XMLUnit.setIgnoreWhitespace(true);
    Diff diff = new Diff(expected, actual);

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
