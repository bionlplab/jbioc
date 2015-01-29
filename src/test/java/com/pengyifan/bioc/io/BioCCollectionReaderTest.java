package com.pengyifan.bioc.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.stream.XMLStreamException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCPassage;

public class BioCCollectionReaderTest {

  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";
//  private static final String DTD = "<!DOCTYPE collection SYSTEM \"BioC.dtd\" []>";
  private static final String DTD = "BioC.dtd";
  
  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_success()
      throws Exception {
    BioCCollectionReader reader = new BioCCollectionReader(
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
  public void test_emptyReader()
      throws Exception {
    thrown.expect(XMLStreamException.class);
    BioCCollectionReader reader = new BioCCollectionReader(new StringReader(""));
    reader.readCollection();
    reader.close();
  }

  @Test
  public void test_readTwice()
      throws Exception {
    BioCCollectionReader reader = new BioCCollectionReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    reader.readCollection();
    // twice
    assertNull(reader.readCollection());
    reader.close();
  }

  @Test
  public void test_dtd()
      throws Exception {
    BioCCollectionReader reader = new BioCCollectionReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection = reader.readCollection();
    assertEquals(DTD, collection.getDtd().getSystemId());
    reader.close();
  }
}
