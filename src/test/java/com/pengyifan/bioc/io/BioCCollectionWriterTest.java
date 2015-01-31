package com.pengyifan.bioc.io;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

public class BioCCollectionWriterTest {

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

    File tmpFile = testFolder.newFile();

    test(c, new BioCCollectionWriter(new FileWriter(tmpFile)), tmpFile);
    test(c, new BioCCollectionWriter(new FileOutputStream(tmpFile)), tmpFile);
    test(c, new BioCCollectionWriter(tmpFile), tmpFile);
    test(c, new BioCCollectionWriter(tmpFile.getAbsolutePath()), tmpFile);
  }

  @Test
  public void test_writeTwice()
      throws Exception {
    URL url = this.getClass().getResource("/" + XML_FILENAME);
    BioCCollectionReader reader = new BioCCollectionReader(url.getFile());
    BioCCollection collection = reader.readCollection();
    reader.close();

    File tmpFile = testFolder.newFile();

    BioCCollectionWriter writer = new BioCCollectionWriter(new FileWriter(
        tmpFile));
    writer.writeCollection(collection);
    // twice
    thrown.expect(IllegalStateException.class);
    writer.writeCollection(collection);
    writer.close();
  }

  private void test(BioCCollection collection,
      BioCCollectionWriter writer,
      File tmpFile)
      throws XMLStreamException, IOException, SAXException {
    writer.writeCollection(collection);
    writer.close();

    // test
    XMLUnit.setIgnoreWhitespace(true);
    URL url = this.getClass().getResource("/" + XML_FILENAME);
    Diff diff = new Diff(new FileReader(url.getFile()),
        new FileReader(tmpFile));
    assertTrue(diff.similar());
  }
}
