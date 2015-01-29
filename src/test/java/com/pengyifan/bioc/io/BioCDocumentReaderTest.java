package com.pengyifan.bioc.io;

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

import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.io.BioCDocumentReader;

public class BioCDocumentReaderTest {

  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";
  private static final String DTD = "<!DOCTYPE collection SYSTEM \"BioC.dtd\" []>";

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_success()
      throws Exception {
    BioCDocumentReader reader = new BioCDocumentReader(
        new InputStreamReader(Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection = reader.readCollectionInfo();
    assertEquals(collection.getDocmentCount(), 0);
    assertEquals(collection.getSource(), "PubMed");
    assertEquals(collection.getKey(), "PMID-8557975-simplified-sentences.key");

    collection = new BioCCollection();

    BioCDocument doc = null;
    while ((doc = reader.readDocument()) != null) {
      // System.out.println(doc);
      collection.addDocument(doc);
    }

    assertEquals(collection.getDocmentCount(), 1);

    doc = collection.getDocument(0);
    assertEquals(doc.getPassageCount(), 1);

    BioCPassage pass = doc.getPassage(0);
    assertEquals(pass.getSentenceCount(), 7);

    reader.close();
  }

  @Test
  public void test_emptyReader()
      throws Exception {
    thrown.expect(XMLStreamException.class);
    BioCDocumentReader reader = new BioCDocumentReader(new StringReader(""));
    reader.readCollectionInfo();
    reader.close();
  }

  @Test
  public void test_readTwice()
      throws Exception {
    BioCDocumentReader reader = new BioCDocumentReader(
        new InputStreamReader(Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection = reader.readCollectionInfo();
    assertEquals(collection.getDocmentCount(), 0);

    @SuppressWarnings("unused")
    BioCDocument doc = null;
    while ((doc = reader.readDocument()) != null) {
      // System.out.println(doc);
    }

    // twice
    collection = reader.readCollectionInfo();
    assertEquals(collection.getDocmentCount(), 0);
    assertNull(reader.readDocument());
    reader.close();
  }

  @Test
  @Ignore
  public void test_dtd()
      throws Exception {
    BioCDocumentReader reader = new BioCDocumentReader(
        new InputStreamReader(Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection = reader.readCollectionInfo();
    assertEquals(DTD, collection.getDtd().getSystemId());
    reader.close();
  }
}
