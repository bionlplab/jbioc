package bioc.test.junit;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import bioc.BioCCollection;
import bioc.io.BioCCollectionReader;
import bioc.io.BioCCollectionWriter;
import bioc.io.BioCFactory;

public class BioCCollectionWriterTest {

  public BioCFactory factory = BioCFactory.newFactory(BioCFactory.STANDARD);
  public String      inXML   = "xml/PMID-8557975-simplified-sentences.xml";
  public String      outXML  = "output/PMID-8557975-simplified-sentences.xml";

  @BeforeClass
  public static void onlyOnce() {

    XMLUnit.setIgnoreWhitespace(true);
    XMLUnit.setIgnoreComments(true);
    XMLUnit.setIgnoreAttributeOrder(true);

    // don't validate dtd
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setValidating(false);
    try {
      dbf.setFeature("http://xml.org/sax/features/namespaces", false);
      dbf.setFeature("http://xml.org/sax/features/validation", false);
      dbf.setFeature(
          "http://apache.org/xml/features/nonvalidating/load-dtd-grammar",
          false);
      dbf.setFeature(
          "http://apache.org/xml/features/nonvalidating/load-external-dtd",
          false);
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }

    XMLUnit.setTestDocumentBuilderFactory(dbf);
    XMLUnit.setControlDocumentBuilderFactory(dbf);
  }

  @Test
  public void testWoodstox()
      throws Exception {
    testXMLIdentical(
        BioCFactory.newFactory(BioCFactory.WOODSTOX),
        inXML,
        outXML + ".woodstox");
  }

  @Test
  public void testStandard()
      throws Exception {
    testXMLIdentical(
        BioCFactory.newFactory(BioCFactory.STANDARD),
        inXML,
        outXML + ".standard");
  }

  @Ignore("Test is ignored as this function is not decided yet")
  @Test(expected = IllegalStateException.class)
  public void testWriteCollectionTwice()
      throws XMLStreamException, IOException {
    BioCCollection collection = new BioCCollection();

    BioCCollectionWriter writer = factory
        .createBioCCollectionWriter(new PrintWriter(System.out));
    writer.writeCollection(collection);
    writer.writeCollection(collection);
    writer.close();
  }

  private
      void
      testXMLIdentical(BioCFactory factory, String inXML, String outXML)
          throws Exception {
    BioCCollectionReader reader = factory
        .createBioCCollectionReader(new FileReader(inXML));
    BioCCollectionWriter writer = factory
        .createBioCCollectionWriter(new FileWriter(outXML));

    BioCCollection collection = reader.readCollection();
    writer.writeCollection(collection);

    reader.close();
    writer.close();

    FileReader controlReader = new FileReader(inXML);
    FileReader testReader = new FileReader(outXML);

    Diff diff = new Diff(controlReader, testReader);

    try {
      XMLAssert.assertTrue("XML similar " + diff.toString(), diff.similar());
    } catch (AssertionError e) {
      DetailedDiff detDiff = new DetailedDiff(diff);
      @SuppressWarnings("rawtypes")
      List allDifferences = detDiff.getAllDifferences();
      for (Object object : allDifferences) {
        Difference difference = (Difference) object;
        System.out.println(difference);
      }
    }
  }
}
