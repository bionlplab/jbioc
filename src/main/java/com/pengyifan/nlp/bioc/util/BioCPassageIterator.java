package com.pengyifan.nlp.bioc.util;

import java.util.Collections;
import java.util.Iterator;

import com.google.common.collect.Iterators;
import com.pengyifan.nlp.bioc.BioCCollection;
import com.pengyifan.nlp.bioc.BioCDocument;
import com.pengyifan.nlp.bioc.BioCPassage;

/**
 * This class provides a simple way to iterate over BioC passages.
 */
public class BioCPassageIterator implements Iterator<BioCPassage> {

  private BioCDocumentIterator documentItr;
  private BioCDocument currentDocument;
  private Iterator<BioCPassage> passageItr;

  public BioCPassageIterator() {
    this(new BioCDocumentIterator());
  }
  
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
  
  public BioCPassageIterator(BioCCollection collection) {
    this(new BioCDocumentIterator(collection));
  }

  public BioCPassageIterator(BioCDocument document) {
    this(new BioCDocumentIterator(document));
  }
  
  public BioCPassageIterator(BioCPassage passage) {
    documentItr = new BioCDocumentIterator();
    currentDocument = null;
    passageItr = Iterators.singletonIterator(passage);
  }

  /**
   * Returns the document that contains current passage.
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
    return passageItr.next();
  }

}
