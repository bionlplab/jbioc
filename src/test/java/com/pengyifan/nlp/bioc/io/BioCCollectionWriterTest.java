package com.pengyifan.nlp.bioc.io;

import static com.pengyifan.nlp.bioc.testing.BioCAssert.assertEquivalentTo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.pengyifan.nlp.bioc.BioCCollection;
import com.pengyifan.nlp.bioc.io.BioCCollectionReader;
import com.pengyifan.nlp.bioc.io.BioCCollectionWriter;

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
    BioCCollectionReader reader = new BioCCollectionReader(
        new InputStreamReader(Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection = reader.readCollection();
    String dtd = reader.getDTD();
    reader.close();

    // write
    File tmpFile = testFolder.newFile();
    BioCCollectionWriter writer = new BioCCollectionWriter(new FileWriter(
        tmpFile));
    writer.setDTD(dtd);
    writer.writeCollection(collection);
    writer.close();

    // test
    assertEquivalentTo(
        new InputStreamReader(Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(XML_FILENAME)),
        new FileReader(tmpFile));
  }
}
