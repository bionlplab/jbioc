package com.pengyifan.bioc.util;

import java.util.Collections;
import java.util.Iterator;

import com.google.common.collect.Iterators;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;

/**
 * A document iterator over a collection
 * 
 * @since 1.0.0
 * @see BioCPassageIterator
 * @see BioCSentenceIterator
 * @author Yifan Peng
 */
public class BioCDocumentIterator implements Iterator<BioCDocument> {

  private Iterator<BioCDocument> documentItr;

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
   * an iterator containing only document.
   * 
   * @param document the BioC document
   */
  public BioCDocumentIterator(BioCDocument document) {
    documentItr = Iterators.singletonIterator(document);
  }

  /**
   * Returns true if the iteration has more documents.
   * 
   * @return if the iteration has more documents
   */
  @Override
  public boolean hasNext() {
    return documentItr.hasNext();
  }

  /**
   * Returns the next document in the iteration.
   * 
   * @return the next document in the iteration
   */
  @Override
  public BioCDocument next() {
    return documentItr.next();
  }

}
