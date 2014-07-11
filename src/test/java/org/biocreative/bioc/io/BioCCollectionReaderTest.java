package org.biocreative.bioc.io;

import static org.junit.Assert.assertEquals;
import java.io.InputStreamReader;
import java.io.StringReader;
import javax.xml.stream.XMLStreamException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.io.BioCCollectionReader;
import org.biocreative.bioc.io.BioCFactory;
import org.biocreative.bioc.io.standard.JdkStrategy;
import org.biocreative.bioc.io.woodstox.WoodstoxStrategy;

public class BioCCollectionReaderTest {
  
  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";
  private static final JdkStrategy JDK_STRATEGY = new JdkStrategy();
  private static final WoodstoxStrategy WOODSTOX_STRATEGY = new WoodstoxStrategy();

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_jdkSuccess()
      throws Exception {
    BioCCollectionReader reader = BioCFactory.newFactory(JDK_STRATEGY)
        .createBioCCollectionReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection = reader.readCollection();
    reader.close();
    assertEquals(collection.getSource(), "PubMed");
    assertEquals(collection.getKey(), "PMID-8557975-simplified-sentences.key");
  }
  
  @Test
  public void test_woodstoxSuccess()
      throws Exception {
    BioCCollectionReader reader = BioCFactory.newFactory(WOODSTOX_STRATEGY)
        .createBioCCollectionReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection = reader.readCollection();
    reader.close();
    
    // more deeper
    assertEquals(collection.getSource(), "PubMed");
    assertEquals(collection.getKey(), "PMID-8557975-simplified-sentences.key");
  }
  
  @Test
  public void test_emptyJdkReader() throws Exception{
    BioCCollectionReader reader = BioCFactory.newFactory(JDK_STRATEGY)
        .createBioCCollectionReader(new StringReader(""));
    thrown.expect(XMLStreamException.class);
    reader.readCollection();
  }
  
  @Test
  public void test_emptyWoodstoxReader() throws Exception{
    BioCCollectionReader reader = BioCFactory.newFactory(WOODSTOX_STRATEGY)
        .createBioCCollectionReader(new StringReader(""));
    thrown.expect(XMLStreamException.class);
    reader.readCollection();
  }
}
