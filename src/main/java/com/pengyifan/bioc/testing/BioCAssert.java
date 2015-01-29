package com.pengyifan.bioc.testing;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.xmlmatchers.transform.XmlConverters.the;
import static org.xmlmatchers.XmlMatchers.isEquivalentTo;
import static org.xmlmatchers.XmlMatchers.isSimilarTo;

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
 */
public class BioCAssert {

  private static final int BUF_SIZE = 1024;

  /**
   * Asserts that a BioC file is valid based on the given dtd file.
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
   * Asserts that a BioC file is valid based on the given dtd file. If the BioC
   * file is invalid, prints the error message.
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
   */
  public static void assertEquivalentTo(Reader controlReader,
      Reader actualReader)
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

    assertEquivalentTo(controlXml.toString(), actualXml.toString());
  }

  /**
   * Asserts that two BioC files are equal. Two documents are considered to be
   * "equivalent" if they contain the same elements in the same order.
   */
  public static void assertEquivalentTo(String controlXml, String actualXml)
      throws IOException {
    assertThat(
        the(controlXml.toString()),
        isEquivalentTo(the(actualXml.toString())));
  }

  /**
   * Asserts that two BioC files are equal. Two documents are considered to be
   * "similar" if the the content of the nodes in the documents are the same,
   * but minor differences exist e.g. sequencing of sibling elements.
   */
  public static void assertSimilarTo(String controlXml, String actualXml)
      throws IOException {
    assertThat(
        the(controlXml.toString()),
        isSimilarTo(the(actualXml.toString())));
  }

  /**
   * Asserts that two BioC files are equal. If two BioC files are not equal,
   * prints the different parts.
   */
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
