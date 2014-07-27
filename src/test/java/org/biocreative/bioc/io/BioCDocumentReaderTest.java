package org.biocreative.bioc.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.stream.XMLStreamException;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.BioCPassage;
import org.biocreative.bioc.io.BioCFactory;
import org.biocreative.bioc.io.standard.JdkStrategy;
import org.biocreative.bioc.io.woodstox.WoodstoxStrategy;

public class BioCDocumentReaderTest {

  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";
  private static final String JDK_DTD = "<!DOCTYPE collection SYSTEM \"BioC.dtd\" []>";
  private static final String WOODSTOX_DTD = "BioC.dtd";
  
  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  @Rule
  public ExpectedException thrown = ExpectedException.none();
  
  @Test
  public void test_success_jdk()
      throws Exception {
    test_success(new JdkStrategy());
  }

  @Test
  public void test_success_woodstox()
      throws Exception {
    test_success(new WoodstoxStrategy());
  }

  private void test_success(BioCXMLStrategy strategy)
      throws Exception {
    BioCDocumentReader reader = BioCFactory.newFactory(strategy)
        .createBioCDocumentReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection = reader.readCollectionInfo();
    assertEquals(collection.getDocmentCount(), 0);
    assertEquals(collection.getSource(), "PubMed");
    assertEquals(collection.getKey(), "PMID-8557975-simplified-sentences.key");

    BioCCollection.Builder collectionBuilder = collection.getBuilder();

    BioCDocument doc = null;
    while ((doc = reader.readDocument()) != null) {
//      System.out.println(doc);
      collectionBuilder.addDocument(doc);
    }
    collection = collectionBuilder.build();

    assertEquals(collection.getDocmentCount(), 1);

    doc = collection.getDocument(0);
    assertEquals(doc.getPassageCount(), 1);

    BioCPassage pass = doc.getPassage(0);
    assertEquals(pass.getSentenceCount(), 7);

    reader.close();
  }

  @Test
  public void test_emptyReader_jdk() throws Exception {
    test_emptyReader(new JdkStrategy());
  }

  @Test
  public void test_emptyReader_woodstox() throws Exception {
    test_emptyReader(new WoodstoxStrategy());
  }
  
  private void test_emptyReader(BioCXMLStrategy strategy)
      throws Exception {
    thrown.expect(XMLStreamException.class);
    BioCDocumentReader reader = BioCFactory.newFactory(strategy)
        .createBioCDocumentReader(new StringReader(""));
    reader.readCollectionInfo();
  }
  
  @Test
  public void test_readTwice_jdk()
      throws Exception {
    test_readTwice(new JdkStrategy());
  }

  @Test
  public void test_readTwice_woodstox()
      throws Exception {
    test_readTwice(new WoodstoxStrategy());
  }

  private void test_readTwice(BioCXMLStrategy strategy)
      throws Exception {
    BioCDocumentReader reader = BioCFactory.newFactory(strategy)
        .createBioCDocumentReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection = reader.readCollectionInfo();
    assertEquals(collection.getDocmentCount(), 0);
    
    BioCDocument doc = null;
    while ((doc = reader.readDocument()) != null) {
//      System.out.println(doc);
    }
    
    // twice
    collection = reader.readCollectionInfo();
    assertEquals(collection.getDocmentCount(), 0);
    assertNull(reader.readDocument());
  }

  @Test
  public void test_dtd_jdk()
      throws Exception {
    test_dtd(new JdkStrategy(), JDK_DTD);
  }

  @Test
  @Ignore
  public void test_dtd_woodstox()
      throws Exception {
    test_dtd(new WoodstoxStrategy(), WOODSTOX_DTD);
  }
  
  private void test_dtd(BioCXMLStrategy strategy, String dtd)
      throws Exception {
    BioCDocumentReader reader = BioCFactory.newFactory(strategy)
        .createBioCDocumentReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    assertEquals(dtd, reader.getDTD());
    reader.close();
  }
}
