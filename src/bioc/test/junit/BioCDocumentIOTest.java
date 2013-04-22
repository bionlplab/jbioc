package bioc.test.junit;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;
import org.junit.Test;

import bioc.BioCCollection;
import bioc.BioCDocument;
import bioc.io.BioCDocumentReader;
import bioc.io.BioCDocumentWriter;
import bioc.io.BioCFactory;

public class BioCDocumentIOTest {

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

  private
      void
      testXMLIdentical(BioCFactory factory, String inXML, String outXML)
          throws Exception {
    BioCDocumentReader reader = factory
        .createBioCDocumentReader(new FileReader(inXML));

    BioCDocumentWriter writer = factory
        .createBioCDocumentWriter(new FileWriter(outXML));

    BioCCollection collection = reader.readCollectionInfo();
    writer.writeCollectionInfo(collection);

    BioCDocument doc = null;
    while ((doc = reader.readDocument()) != null) {
      writer.writeDocument(doc);
    }

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
