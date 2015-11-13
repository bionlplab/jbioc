package com.pengyifan.bioc.util;

import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.BioCSentence;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class BioCSearchTest {

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
  public void test_getSentence() throws Exception {
    BioCSearch search = new BioCSearch(EXPECTED_COLLECTION);
    Optional<BioCSentence> opt = search.getSentence("1", 1);
    assertTrue(opt.isPresent());

    opt = search.getSentence("1", 4);
    assertFalse(opt.isPresent());
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