package com.pengyifan.bioc.io;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.stream.XMLStreamException;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.xml.sax.SAXException;

import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;

public class BioCDocumentWriterTest {

  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_success()
      throws Exception {
    // read
    URL url = this.getClass().getResource("/" + XML_FILENAME);
    BioCCollectionReader reader = new BioCCollectionReader(url.getFile());
    BioCCollection c = reader.readCollection();
    reader.close();

    // write
    File tmpFile = testFolder.newFile();
    test(c, new BioCDocumentWriter(new FileWriter(tmpFile)), tmpFile);
    test(c, new BioCDocumentWriter(new FileOutputStream(tmpFile)), tmpFile);
    test(c, new BioCDocumentWriter(tmpFile), tmpFile);
    test(c, new BioCDocumentWriter(tmpFile.getAbsolutePath()), tmpFile);
  }

  @Test
  public void test_writeBeginCollectionInfoTwice()
      throws Exception {
    // read
    URL url = this.getClass().getResource("/" + XML_FILENAME);
    BioCCollectionReader reader = new BioCCollectionReader(url.getFile());
    BioCCollection collection = reader.readCollection();
    reader.close();

    // write
    File tmpFile = testFolder.newFile();
    BioCDocumentWriter writer = new BioCDocumentWriter(tmpFile);
    writer.writeBeginCollectionInfo(collection);
    // twice
    thrown.expect(IllegalStateException.class);
    writer.writeBeginCollectionInfo(collection);
    writer.close();
  }

  @Test
  public void test_NotWriteBeginCollectionInfo()
      throws Exception {
    // read
    BioCCollectionReader reader = new BioCCollectionReader(
        new InputStreamReader(Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection = reader.readCollection();
    reader.close();

    // write
    File tmpFile = testFolder.newFile();
    BioCDocumentWriter writer = new BioCDocumentWriter(tmpFile);
    thrown.expect(IllegalStateException.class);
    writer.writeDocument(collection.getDocuments().get(0));
    writer.close();
  }
  
  private void test(BioCCollection collection,
      BioCDocumentWriter writer,
      File tmpFile)
      throws XMLStreamException, IOException, SAXException {
    writer.writeBeginCollectionInfo(collection);
    for (BioCDocument document : collection.getDocuments()) {
      writer.writeDocument(document);
    }
    writer.close();

    // test
    XMLUnit.setIgnoreWhitespace(true);
    URL url = this.getClass().getResource("/" + XML_FILENAME);
    Diff diff = new Diff(new FileReader(url.getFile()), new FileReader(tmpFile));
    assertTrue(diff.similar());
  }
}
