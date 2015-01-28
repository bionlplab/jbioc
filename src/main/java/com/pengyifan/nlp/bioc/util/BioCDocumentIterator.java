package com.pengyifan.nlp.bioc.util;

import java.util.Collections;
import java.util.Iterator;

import com.google.common.collect.Iterators;
import com.pengyifan.nlp.bioc.BioCCollection;
import com.pengyifan.nlp.bioc.BioCDocument;

public class BioCDocumentIterator implements Iterator<BioCDocument> {

  private Iterator<BioCDocument> documentItr;
  
  public BioCDocumentIterator() {
    documentItr = Collections.emptyIterator();
  }
  
  public BioCDocumentIterator(BioCCollection collection) {
    documentItr = collection.getDocuments().iterator();
  }
  
  public BioCDocumentIterator(BioCDocument document) {
    documentItr = Iterators.singletonIterator(document);
  }

  @Override
  public boolean hasNext() {
    return documentItr.hasNext();
  }

  @Override
  public BioCDocument next() {
    return documentItr.next();
  }

}
