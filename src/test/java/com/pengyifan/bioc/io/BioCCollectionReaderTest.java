package com.pengyifan.bioc.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
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

public class BioCCollectionReaderTest {

  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";
  // private static final String DTD =
  // "<!DOCTYPE collection SYSTEM \"BioC.dtd\" []>";
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
    test(new BioCCollectionReader(new FileReader(file)));
    test(new BioCCollectionReader(new FileInputStream(file)));
    test(new BioCCollectionReader(file));
    test(new BioCCollectionReader(file.getAbsolutePath()));
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
    URL url = this.getClass().getResource("/" + XML_FILENAME);
    BioCCollectionReader reader = new BioCCollectionReader(url.getFile());
    reader.readCollection();
    // twice
    assertNull(reader.readCollection());
    reader.close();
  }

  private void test(BioCCollectionReader reader)
      throws Exception {
    BioCCollection collection = reader.readCollection();
    reader.close();
    assertEquals(collection.getSource(), "PubMed");
    assertEquals(collection.getKey(), "PMID-8557975-simplified-sentences.key");

    BioCDocument doc = collection.getDocument(0);
    assertEquals(doc.getPassageCount(), 1);

    BioCPassage pass = doc.getPassage(0);
    assertEquals(pass.getSentenceCount(), 7);
  }
}
