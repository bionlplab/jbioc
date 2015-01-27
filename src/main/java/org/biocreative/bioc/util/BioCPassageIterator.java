package org.biocreative.bioc.util;

import java.util.Iterator;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.BioCPassage;

/**
 * This class provides a simple way to iterate over BioC passages.
 */
public class BioCPassageIterator implements Iterator<BioCPassage> {

  private Iterator<BioCDocument> documentItr;
  private BioCDocument           currentDocument;
  private Iterator<BioCPassage>  passageItr;

  public BioCPassageIterator(BioCCollection collection) {
    documentItr = collection.getDocuments().iterator();
    if (documentItr.hasNext()) {
      currentDocument = documentItr.next();
      passageItr = currentDocument.getPassages().iterator();
    } else {
      currentDocument = null;
      passageItr = null;
    }
  }

  /**
   * Returns the document that contains current passage.
   */
  public BioCDocument getDocument() {
    return currentDocument;
  }

  @Override
  public boolean hasNext() {
    if (passageItr == null) {
      return false;
    } else if (passageItr.hasNext()) {
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

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }
}
