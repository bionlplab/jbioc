package org.biocreative.bioc.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.util.BioCSentenceIterator;
import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCSentence;
import org.biocreative.bioc.io.BioCCollectionReader;
import org.biocreative.bioc.io.BioCFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Test BioCCollectionReader and BioCCollectionWriter
 */
public class BioCSentenceIteratorTest {

  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";
  private static final String FLAG = BioCFactory.STANDARD;

  private static final String EXPECTED_SEN_0 = "Active Raf-1 phosphorylates and activates "
      + "the mitogen-activated protein (MAP) kinase/extracellular signal-regulated kinase "
      + "kinase 1 (MEK1), which in turn phosphorylates and activates the MAP "
      + "kinases/extracellular signal regulated kinases, ERK1 and ERK2.";

  private static final String EXPECTED_SEN_1 = "Active Raf-1 phosphorylates MEK1.";

  private static final String EXPECTED_SEN_2 = "Active Raf-1 activates MEK1.";

  private static final String EXPECTED_SEN_3 = "MEK1 in turn phosphorylates ERK1.";

  private static final String EXPECTED_SEN_4 = "MEK1 in turn phosphorylates ERK2.";

  private static final String EXPECTED_SEN_5 = "MEK1 in turn activates ERK1.";

  private static final String EXPECTED_SEN_6 = "MEK1 in turn activates ERK2.";

  @Test
  public void test_success()
      throws XMLStreamException, IOException {
    BioCCollectionReader reader = BioCFactory.newFactory(FLAG)
        .createBioCCollectionReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection = reader.readCollection();
    reader.close();

    List<String> sentences = new ArrayList<String>();
    BioCSentenceIterator itr = new BioCSentenceIterator(collection);
    while (itr.hasNext()) {
      BioCSentence sentence = itr.next();
      sentences.add(sentence.getText());
      System.out.println(sentence.getText());
    }

    assertEquals(sentences.size(), 7);
    assertThat(
        sentences,
        hasItems(
            EXPECTED_SEN_0,
            EXPECTED_SEN_1,
            EXPECTED_SEN_2,
            EXPECTED_SEN_3,
            EXPECTED_SEN_4,
            EXPECTED_SEN_5,
            EXPECTED_SEN_6));
  }

  @Test
  public void test_empty() {
    BioCCollection collection = new BioCCollection();
    BioCSentenceIterator itr = new BioCSentenceIterator(collection);
    assertFalse(itr.hasNext());
  }
}
