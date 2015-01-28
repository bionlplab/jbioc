package com.pengyifan.nlp.bioc.util;

import java.util.Collections;
import java.util.Iterator;

import com.google.common.collect.Iterators;
import com.pengyifan.nlp.bioc.BioCCollection;
import com.pengyifan.nlp.bioc.BioCDocument;
import com.pengyifan.nlp.bioc.BioCPassage;
import com.pengyifan.nlp.bioc.BioCSentence;

/**
 * This class provides a simple way to iterate over BioC sentences.
 */
public class BioCSentenceIterator implements Iterator<BioCSentence> {

  private BioCPassageIterator passageItr;
  private BioCPassage currentPassage;
  private Iterator<BioCSentence> sentenceItr;

  public BioCSentenceIterator() {
    this(new BioCPassageIterator());
  }

  public BioCSentenceIterator(BioCPassageIterator passageItr) {
    this.passageItr = passageItr;
    if (passageItr.hasNext()) {
      currentPassage = passageItr.next();
      sentenceItr = currentPassage.getSentences().iterator();
    } else {
      currentPassage = null;
      sentenceItr = Collections.emptyIterator();
    }
  }

  public BioCSentenceIterator(BioCCollection collection) {
    this(new BioCPassageIterator(collection));
  }

  public BioCSentenceIterator(BioCDocument document) {
    this(new BioCPassageIterator(document));
  }

  public BioCSentenceIterator(BioCPassage passage) {
    this(new BioCPassageIterator(passage));
  }

  public BioCSentenceIterator(BioCSentence sentence) {
    passageItr = new BioCPassageIterator();
    currentPassage = null;
    sentenceItr = Iterators.singletonIterator(sentence);
  }

  /**
   * Returns the passage that contains current sentence.
   */
  public BioCDocument getDocument() {
    return passageItr.getDocument();
  }

  /**
   * Returns the passage that contains current sentence.
   */
  public BioCPassage getPassage() {
    return currentPassage;
  }

  @Override
  public boolean hasNext() {
    if (sentenceItr.hasNext()) {
      return true;
    } else if (passageItr.hasNext()) {
      currentPassage = passageItr.next();
      sentenceItr = currentPassage.getSentences().iterator();
      return hasNext();
    } else {
      return false;
    }
  }

  @Override
  public BioCSentence next() {
    return sentenceItr.next();
  }

}
