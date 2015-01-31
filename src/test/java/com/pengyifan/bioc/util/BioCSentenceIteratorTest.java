package com.pengyifan.bioc.util;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.BioCSentence;

/**
 * Test BioCCollectionReader and BioCCollectionWriter
 */
public class BioCSentenceIteratorTest {

  private static final String DATE = "date";
  private static final String KEY = "key";
  private static final String SOURCE = "source";

  private static final BioCSentence EXPECTED_SEN_0 = createSentence(
      0,
      "Active Raf-1 phosphorylates and activates the mitogen-activated protein "
          + "(MAP) kinase/extracellular signal-regulated kinase kinase 1 (MEK1), "
          + "which in turn phosphorylates and activates the MAP "
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

  private static final BioCPassage EXPECTED_PASSAGE_0 = createPassage(
      0,
      EXPECTED_SEN_0);
  private static final BioCPassage EXPECTED_PASSAGE_1 = createPassage(
      1, EXPECTED_SEN_1, EXPECTED_SEN_2, EXPECTED_SEN_3);

  private static final BioCDocument EXPECTED_DOCUMENT = createDocument(
      "1",
      EXPECTED_PASSAGE_0,
      EXPECTED_PASSAGE_1);

  private static final BioCCollection EXPECTED_COLLECTION = createCollection(
      DATE, KEY, SOURCE, EXPECTED_DOCUMENT);

  @Test
  public void test_success()
      throws XMLStreamException, IOException {
    BioCSentenceIterator itr = new BioCSentenceIterator(EXPECTED_COLLECTION);
    List<BioCSentence> actual = Lists.newArrayList(itr);
    assertThat(actual, contains(EXPECTED_SEN_0, EXPECTED_SEN_1,
        EXPECTED_SEN_2, EXPECTED_SEN_3));

    itr = new BioCSentenceIterator(EXPECTED_COLLECTION);
    assertTrue(itr.hasNext());
    itr.next();
    assertEquals(EXPECTED_DOCUMENT, itr.getDocument());
    assertEquals(EXPECTED_PASSAGE_0, itr.getPassage());

    assertTrue(itr.hasNext());
    itr.next();
    assertEquals(EXPECTED_PASSAGE_1, itr.getPassage());
  }

  @Test
  public void test_constructorSentence() {
    BioCSentenceIterator itr = new BioCSentenceIterator(EXPECTED_SEN_0);
    List<BioCSentence> actual = Lists.newArrayList(itr);
    assertThat(actual, contains(EXPECTED_SEN_0));
  }

  @Test
  public void test_constructorPassage() {
    BioCSentenceIterator itr = new BioCSentenceIterator(EXPECTED_PASSAGE_1);
    List<BioCSentence> actual = Lists.newArrayList(itr);
    assertThat(actual, contains(EXPECTED_SEN_1, EXPECTED_SEN_2, EXPECTED_SEN_3));
  }

  @Test
  public void test_constructorDocument() {
    BioCSentenceIterator itr = new BioCSentenceIterator(EXPECTED_DOCUMENT);
    List<BioCSentence> actual = Lists.newArrayList(itr);
    assertThat(actual, contains(EXPECTED_SEN_0, EXPECTED_SEN_1, EXPECTED_SEN_2,
        EXPECTED_SEN_3));
  }

  @Test
  public void test_constructorEmpty() {
    BioCSentenceIterator itr = new BioCSentenceIterator();
    List<BioCSentence> actual = Lists.newArrayList(itr);
    assertTrue(actual.isEmpty());
  }

  @Test
  public void test_empty() {
    BioCCollection collection = new BioCCollection();
    collection.setDate(DATE);
    collection.setKey(KEY);
    collection.setSource(SOURCE);
    BioCSentenceIterator itr = new BioCSentenceIterator(collection);
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

  private static BioCPassage createPassage(int offset,
      BioCSentence... sentences) {
    BioCPassage passage = new BioCPassage();
    passage.setOffset(offset);
    for (BioCSentence sentence : sentences) {
      passage.addSentence(sentence);
    }
    return passage;
  }

  private static BioCSentence createSentence(int offset, String text) {
    BioCSentence sentence = new BioCSentence();
    sentence.setOffset(offset);
    sentence.setText(text);
    return sentence;
  }
}
