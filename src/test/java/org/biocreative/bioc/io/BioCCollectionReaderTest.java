package org.biocreative.bioc.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.stream.XMLStreamException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.BioCPassage;
import org.biocreative.bioc.io.BioCCollectionReader;
import org.biocreative.bioc.io.BioCFactory;
import org.biocreative.bioc.io.standard.JdkStrategy;
import org.biocreative.bioc.io.woodstox.WoodstoxStrategy;

public class BioCCollectionReaderTest {
  
  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";
  private static final BioCXMLStrategy STRATEGY = new WoodstoxStrategy();
  private static final String DTD = "<!DOCTYPE collection SYSTEM \"BioC.dtd\" []>";

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_success()
      throws Exception {
    BioCCollectionReader reader = BioCFactory.newFactory(STRATEGY)
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
  public void test_emptyReader() throws Exception{
    thrown.expect(XMLStreamException.class);
    BioCFactory.newFactory(STRATEGY)
        .createBioCCollectionReader(new StringReader(""));
  }
  
  @Test
  public void test_readTwice() throws Exception{
    BioCCollectionReader reader = BioCFactory.newFactory(STRATEGY)
        .createBioCCollectionReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    reader.readCollection();
    // twice
    assertNull(reader.readCollection());
  }
  
  @Test
  public void test_dtd()
      throws Exception {
    BioCCollectionReader reader = BioCFactory.newFactory(STRATEGY)
        .createBioCCollectionReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    reader.readCollection();
    assertEquals(reader.getDTD(), DTD);
    reader.close();
  }
}
