package com.pengyifan.bioc.util;

import java.util.Collections;
import java.util.Iterator;

import com.google.common.collect.Iterators;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;

/**
 * A document iterator over a collection or a single document.
 * 
 * @since 1.0.0
 * @see BioCPassageIterator
 * @see BioCSentenceIterator
 * @author Yifan Peng
 */
public class BioCDocumentIterator implements BioCIterator<BioCDocument> {

  private Iterator<BioCDocument> documentItr;
  private BioCDocument currentDocument;

  /**
   * Creates an empty iterator.
   */
  public BioCDocumentIterator() {
    documentItr = Collections.emptyIterator();
  }

  /**
   * Creates an iterator given the BioC collection.
   * 
   * @param collection the BioC collection
   */
  public BioCDocumentIterator(BioCCollection collection) {
    documentItr = collection.getDocuments().iterator();
  }

  /**
   * Creates an iterator containing only document.
   * 
   * @param document the BioC document
   */
  public BioCDocumentIterator(BioCDocument document) {
    documentItr = Iterators.singletonIterator(document);
  }

  @Override
  public boolean hasNext() {
    return documentItr.hasNext();
  }

  @Override
  public BioCDocument next() {
    currentDocument = documentItr.next();
    return currentDocument;
  }

  @Override
  public BioCDocument current() {
    if (currentDocument == null) {
      throw new IllegalStateException();
    }
    return currentDocument;
  }
}
