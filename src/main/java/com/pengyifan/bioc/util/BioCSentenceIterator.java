package com.pengyifan.bioc.util;

import java.util.Collections;
import java.util.Iterator;

import com.google.common.collect.Iterators;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.BioCSentence;

/**
 * A sentence iterator over a collection, a document, a passage or a single
 * sentence.
 * 
 * @since 1.0.0
 * @see BioCDocumentIterator
 * @see BioCPassageIterator
 * @author Yifan Peng
 */
public class BioCSentenceIterator implements Iterator<BioCSentence> {

  private BioCPassageIterator passageItr;
  private BioCPassage currentPassage;
  private Iterator<BioCSentence> sentenceItr;

  /**
   * Creates an empty iterator.
   */
  public BioCSentenceIterator() {
    this(new BioCPassageIterator());
  }

  /**
   * Creates an iterator given the BioCPassageIterator.
   * 
   * @param passageItr the BioCPassageIterator
   */
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

  /**
   * Creates an iterator given the BioC collection.
   * 
   * @param collection the BioC collection
   */
  public BioCSentenceIterator(BioCCollection collection) {
    this(new BioCPassageIterator(collection));
  }

  /**
   * Creates an iterator given the BioC document.
   * 
   * @param document the BioC collection
   */
  public BioCSentenceIterator(BioCDocument document) {
    this(new BioCPassageIterator(document));
  }

  /**
   * Creates an iterator given the BioC passage.
   * 
   * @param passage the BioC collection
   */
  public BioCSentenceIterator(BioCPassage passage) {
    this(new BioCPassageIterator(passage));
  }

  /**
   * Creates an iterator containing only sentence.
   * 
   * @param sentence the BioC sentence
   */
  public BioCSentenceIterator(BioCSentence sentence) {
    passageItr = new BioCPassageIterator();
    currentPassage = null;
    sentenceItr = Iterators.singletonIterator(sentence);
  }

  /**
   * Returns the document that contains current sentence.
   * 
   * @return the document that contains current sentence
   */
  public BioCDocument getDocument() {
    return passageItr.getDocument();
  }

  /**
   * Returns the passage that contains current sentence.
   * 
   * @return the passage that contains current sentence
   */
  public BioCPassage getPassage() {
    return currentPassage;
  }

  /**
   * Returns true if the iteration has more sentences.
   * 
   * @return if the iteration has more sentences
   */
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

  /**
   * Returns the next sentence in the iteration.
   * 
   * @return the next sentence in the iteration
   */
  @Override
  public BioCSentence next() {
    return sentenceItr.next();
  }

}
