package org.biocreative.bioc.io;

import static org.biocreative.bioc.testing.BioCAssert.assertEquivalentTo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.io.standard.JdkStrategy;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class BioCCollectionWriterTest {

  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_success_jdk()
      throws Exception {
    test_success(new JdkStrategy());
  }

  private void test_success(BioCXMLStrategy strategy)
      throws Exception {
    // read
    BioCCollectionReader reader = BioCFactory.newFactory(strategy)
        .createBioCCollectionReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection = reader.readCollection();
    String dtd = reader.getDTD();
    reader.close();

    // write
    File tmpFile = testFolder.newFile();
    BioCCollectionWriter writer = BioCFactory.newFactory(strategy)
        .createBioCCollectionWriter(new FileWriter(tmpFile));
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
