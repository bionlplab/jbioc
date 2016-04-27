package com.pengyifan.bioc.util;

import java.util.Collections;
import java.util.Iterator;

import com.google.common.collect.Iterators;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCPassage;

/**
 * A passage iterator over a collection, a document or a single passage.
 * 
 * @since 1.0.0
 * @see BioCDocumentIterator
 * @see BioCSentenceIterator
 * @author Yifan Peng
 */
public class BioCPassageIterator implements Iterator<BioCPassage>, BioCIterator<BioCPassage> {

  private BioCDocumentIterator documentItr;
  private BioCDocument currentDocument;
  private BioCPassage currentPassage;
  private Iterator<BioCPassage> passageItr;

  /**
   * Creates an empty iterator.
   */
  public BioCPassageIterator() {
    this(new BioCDocumentIterator());
  }

  /**
   * Creates an iterator given the BioCDocumentIterator.
   * 
   * @param documentItr the BioCDocumentIterator
   */
  public BioCPassageIterator(BioCDocumentIterator documentItr) {
    this.documentItr = documentItr;
    if (documentItr.hasNext()) {
      currentDocument = documentItr.next();
      passageItr = currentDocument.getPassages().iterator();
    } else {
      currentDocument = null;
      passageItr = Collections.emptyIterator();
    }
  }

  /**
   * Creates an iterator given the BioC collection.
   * 
   * @param collection the BioC collection
   */
  public BioCPassageIterator(BioCCollection collection) {
    this(new BioCDocumentIterator(collection));
  }

  /**
   * Creates an iterator given the BioC document.
   * 
   * @param document the BioC collection
   */
  public BioCPassageIterator(BioCDocument document) {
    this(new BioCDocumentIterator(document));
  }

  /**
   * Creates an iterator containing only passage.
   * 
   * @param passage the BioC passage
   */
  public BioCPassageIterator(BioCPassage passage) {
    documentItr = new BioCDocumentIterator();
    currentDocument = null;
    passageItr = Iterators.singletonIterator(passage);
  }

  /**
   * Returns the document that contains current passage.
   * 
   * @return the document that contains current passage
   */
  public BioCDocument getDocument() {
    return currentDocument;
  }

  @Override
  public boolean hasNext() {
    if (passageItr.hasNext()) {
      return true;
    } else if (documentItr.hasNext()) {
      currentDocument = documentItr.next();
      passageItr = currentDocument.getPassages().iterator();
      return hasNext();
    } else {
      return false;
    }
  }

  @Override
  public BioCPassage next() {
    currentPassage = passageItr.next();
    return currentPassage;
  }

  @Override
  public BioCPassage current() {
    if (currentPassage == null) {
      throw new IllegalStateException();
    }
    return currentPassage;
  }
}
