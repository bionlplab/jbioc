package org.biocreative.bioc.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.io.BioCCollectionReader;
import org.biocreative.bioc.io.BioCCollectionWriter;
import org.biocreative.bioc.io.BioCFactory;

public class BioCCollectionReaderTest {
  
  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";
  private static final String FLAG_STANDARD = BioCFactory.STANDARD;
  private static final String FLAG_WOODSTOX = BioCFactory.WOODSTOX;

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_success()
      throws Exception {
    BioCCollectionReader reader = BioCFactory.newFactory(FLAG_STANDARD)
        .createBioCCollectionReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection_standard = reader.readCollection();
    reader.close();
    
    reader = BioCFactory.newFactory(FLAG_WOODSTOX)
        .createBioCCollectionReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection_woodstox = reader.readCollection();
    reader.close();
    
    assertEquals(collection_standard, collection_woodstox);
    
    // more deeper
    assertEquals(collection_standard.getSource(), "PubMed");
    assertEquals(collection_standard.getKey(), "PMID-8557975-simplified-sentences.key");
  }
  
  @Test
  public void test_emptyReader() throws Exception{
    BioCCollectionReader reader = BioCFactory.newFactory(FLAG_STANDARD)
        .createBioCCollectionReader(new StringReader(""));
    BioCCollection collection_standard = reader.readCollection();
    reader.close();
    
    assertNull(collection_standard.getSource());
  }
}
