package org.biocreative.bioc.util;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.BioCPassage;
import org.biocreative.bioc.BioCSentence;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Test BioCCollectionReader and BioCCollectionWriter
 */
public class BioCSentenceIteratorTest {

  private static final String DATE = "date";
  private static final String KEY = "key";
  private static final String SOURCE = "source";

  private static final BioCSentence EXPECTED_SEN_0 = createSentence(
      0,
      "Active Raf-1 phosphorylates and activates "
          + "the mitogen-activated protein (MAP) kinase/extracellular signal-regulated kinase "
          + "kinase 1 (MEK1), which in turn phosphorylates and activates the MAP "
          + "kinases/extracellular signal regulated kinases, ERK1 and ERK2.");
  private static final BioCSentence EXPECTED_SEN_1 = createSentence(
      1,
      "Active Raf-1 phosphorylates MEK1.");
  private static final BioCSentence EXPECTED_SEN_2 = createSentence(
      2,
      "Active Raf-1 activates MEK1.");
  private static final BioCSentence EXPECTED_SEN_3 = createSentence(
      3,
      "MEK1 in turn phosphorylates ERK1.");

  private static final BioCCollection COLLECTION = BioCCollection.newBuilder()
      .setDate(DATE)
      .setKey(KEY)
      .setSource(SOURCE)
      .addDocument(BioCDocument.newBuilder()
          .setID("1")
          .addPassage(BioCPassage.newBuilder()
              .setOffset(0)
              .addSentence(EXPECTED_SEN_0)
              .build())
          .addPassage(BioCPassage.newBuilder()
              .setOffset(1)
              .addSentence(EXPECTED_SEN_1)
              .addSentence(EXPECTED_SEN_2)
              .addSentence(EXPECTED_SEN_3)
              .build())
          .build())
      .build();

  @Test
  public void test_success()
      throws XMLStreamException, IOException {
    List<BioCSentence> sentences = Lists.newArrayList();
    BioCSentenceIterator itr = new BioCSentenceIterator(COLLECTION);
    while (itr.hasNext()) {
      BioCSentence sentence = itr.next();
      sentences.add(sentence);
    }

    assertEquals(sentences.size(), 4);
    assertThat(
        sentences,
        hasItems(
            EXPECTED_SEN_0,
            EXPECTED_SEN_1,
            EXPECTED_SEN_2,
            EXPECTED_SEN_3));
  }

  @Test
  public void test_empty() {
    BioCCollection collection = BioCCollection.newBuilder()
        .setDate(DATE)
        .setKey(KEY)
        .setSource(SOURCE)
        .build();
    BioCSentenceIterator itr = new BioCSentenceIterator(collection);
    assertFalse(itr.hasNext());
  }

  private static BioCSentence createSentence(int offset, String text) {
    BioCSentence sentence = new BioCSentence();
    sentence.setOffset(offset);
    sentence.setText(text);
    return sentence;
  }
}
