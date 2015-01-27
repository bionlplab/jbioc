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
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Test BioCCollectionReader and BioCCollectionWriter
 */
public class BioCPassageIteratorTest {

  private static final String DATE = "date";
  private static final String KEY = "key";
  private static final String SOURCE = "source";

  private static final BioCPassage EXPECTED_PASSAGE_0 = createPassage(
      0,
      "Active Raf-1 phosphorylates and activates the mitogen-activated protein "
          + "(MAP) kinase/extracellular signal-regulated kinase kinase 1 (MEK1), "
          + "which in turn phosphorylates and activates the MAP "
          + "kinases/extracellular signal regulated kinases, ERK1 and ERK2.");
  private static final BioCPassage EXPECTED_PASSAGE_1 = createPassage(
      1,
      "Active Raf-1 phosphorylates MEK1."
          + "Active Raf-1 activates MEK1."
          + "MEK1 in turn phosphorylates ERK1.");

  private static final BioCCollection COLLECTION = createCollection(
      DATE,
      KEY,
      SOURCE,
      createDocument("1", EXPECTED_PASSAGE_0, EXPECTED_PASSAGE_1));

  @Test
  public void test_success()
      throws XMLStreamException, IOException {
    List<BioCPassage> passages = Lists.newArrayList();
    BioCPassageIterator itr = new BioCPassageIterator(COLLECTION);
    while (itr.hasNext()) {
      BioCPassage passage = itr.next();
      // System.out.println(passage);
      passages.add(passage);
    }

    assertEquals(passages.size(), 2);
    assertThat(
        passages,
        hasItems(
            EXPECTED_PASSAGE_0,
            EXPECTED_PASSAGE_1));
  }

  @Test
  public void test_empty() {
    BioCCollection collection = new BioCCollection();
    collection.setDate(DATE);
    collection.setKey(KEY);
    collection.setSource(SOURCE);
    BioCPassageIterator itr = new BioCPassageIterator(collection);
    assertFalse(itr.hasNext());
  }

  private static BioCCollection createCollection(String date,
      String key,
      String source,
      BioCDocument... documents) {
    BioCCollection collection = new BioCCollection();
    collection.setDate(date);
    collection.setKey(key);
    collection.setSource(source);
    for (BioCDocument document : documents) {
      collection.addDocument(document);
    }
    return collection;
  }

  private static BioCDocument createDocument(String id,
      BioCPassage... passages) {
    BioCDocument document = new BioCDocument();
    document.setID(id);
    for (BioCPassage passage : passages) {
      document.addPassage(passage);
    }
    return document;
  }

  private static BioCPassage createPassage(int offset, String text) {
    BioCPassage passage = new BioCPassage();
    passage.setOffset(offset);
    passage.setText(text);
    return passage;
  }
}
