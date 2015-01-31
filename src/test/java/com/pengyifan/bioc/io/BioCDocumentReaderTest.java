package com.pengyifan.bioc.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.xml.stream.XMLStreamException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCPassage;

public class BioCDocumentReaderTest {

  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";
  private static final String DTD = "BioC.dtd";

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_success()
      throws Exception {
    URL url = this.getClass().getResource("/" + XML_FILENAME);
    File file = new File(url.getFile());
    test(new BioCDocumentReader(new FileInputStream(file)));
    test(new BioCDocumentReader(new FileReader(file)));
    test(new BioCDocumentReader(file));
    test(new BioCDocumentReader(file.getAbsolutePath()));
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
    URL url = this.getClass().getResource("/" + XML_FILENAME);
    BioCDocumentReader reader = new BioCDocumentReader(url.getFile());
    BioCCollection collection = reader.readCollectionInfo();
    assertEquals(collection.getDocmentCount(), 0);

    @SuppressWarnings("unused")
    BioCDocument doc = null;
    while ((doc = reader.readDocument()) != null) {
    }

    // twice
    collection = reader.readCollectionInfo();
    assertEquals(collection.getDocmentCount(), 0);
    assertNull(reader.readDocument());
    reader.close();
  }

  @Test
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

  private void test(BioCDocumentReader reader)
      throws XMLStreamException, IOException {
    BioCCollection collection = reader.readCollectionInfo();
    assertEquals(collection.getDocmentCount(), 0);
    assertEquals(collection.getSource(), "PubMed");
    assertEquals(collection.getKey(), "PMID-8557975-simplified-sentences.key");

    collection = new BioCCollection();

    BioCDocument doc = null;
    while ((doc = reader.readDocument()) != null) {
      collection.addDocument(doc);
    }

    assertEquals(collection.getDocmentCount(), 1);

    doc = collection.getDocument(0);
    assertEquals(doc.getPassageCount(), 1);

    BioCPassage pass = doc.getPassage(0);
    assertEquals(pass.getSentenceCount(), 7);

    reader.close();
  }
}
