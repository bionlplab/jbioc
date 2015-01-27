package org.biocreative.bioc.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.BioCPassage;
import org.biocreative.bioc.io.standard.JdkStrategy;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class BioCCollectionReaderTest {

  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";
  private static final String JDK_DTD = "<!DOCTYPE collection SYSTEM \"BioC.dtd\" []>";
  
  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  public void test_success_jdk()
      throws Exception {
    test_success(new JdkStrategy());
  }

  private void test_success(BioCXMLStrategy strategy)
      throws Exception {
    BioCCollectionReader reader = BioCFactory.newFactory(strategy)
        .createBioCCollectionReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection = reader.readCollection();
    reader.close();
    assertEquals(collection.getSource(), "PubMed");
    assertEquals(collection.getKey(), "PMID-8557975-simplified-sentences.key");

    BioCDocument doc = collection.getDocument(0);
    assertEquals(doc.getPassageCount(), 1);

    BioCPassage pass = doc.getPassage(0);
    assertEquals(pass.getSentenceCount(), 7);
  }

  @Test
  public void test_emptyReader_jdk()
      throws Exception {
    test_emptyReader(new JdkStrategy());
  }

  private void test_emptyReader(BioCXMLStrategy strategy)
      throws Exception {
    thrown.expect(XMLStreamException.class);
    BioCFactory.newFactory(strategy)
        .createBioCCollectionReader(new StringReader("")).readCollection();
  }

  @Test
  public void test_readTwice_jdk()
      throws Exception {
    test_readTwice(new JdkStrategy());
  }

  private void test_readTwice(BioCXMLStrategy strategy)
      throws Exception {
    BioCCollectionReader reader = BioCFactory.newFactory(strategy)
        .createBioCCollectionReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    reader.readCollection();
    // twice
    assertNull(reader.readCollection());
  }

  @Test
  public void test_dtd_jdk()
      throws Exception {
    test_dtd(new JdkStrategy(), JDK_DTD);
  }

  private void test_dtd(BioCXMLStrategy strategy, String dtd)
      throws Exception {
    BioCCollectionReader reader = BioCFactory.newFactory(strategy)
        .createBioCCollectionReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    reader.readCollection();
    assertEquals(dtd, reader.getDTD());
    reader.close();
  }
}
