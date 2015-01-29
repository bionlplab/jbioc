package com.pengyifan.bioc.util;

import com.pengyifan.bioc.BioCAnnotation;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.BioCSentence;

public class BioCAnnotationSearch {

  private String annotationID;
  private BioCAnnotation annotation;
  private Object container;

  private BioCCollection collection;
  private BioCDocument document;
  private BioCPassage passage;
  private BioCSentence sentence;

  public BioCAnnotationSearch(BioCCollection collection, String annotationID) {
    this.collection = collection;
    this.annotationID = annotationID;
  }

  public BioCAnnotationSearch(BioCDocument document, String annotationID) {
    this.document = document;
    this.annotationID = annotationID;
  }

  public BioCAnnotationSearch(BioCPassage passage, String annotationID) {
    this.passage = passage;
    this.annotationID = annotationID;
  }

  public BioCAnnotationSearch(BioCSentence sentence, String annotationID) {
    this.sentence = sentence;
    this.annotationID = annotationID;
  }

  public boolean find() {
    boolean found = false;
    if (collection != null) {
      found = find(new BioCDocumentIterator(collection));
      if (!found) {
        found = find(new BioCPassageIterator(collection));
      }
      if (!found) {
        found = find(new BioCSentenceIterator(collection));
      }
    } else if (document != null) {
      found = find(new BioCDocumentIterator(document));
      if (!found) {
        found = find(new BioCPassageIterator(document));
      }
      if (!found) {
        found = find(new BioCSentenceIterator(document));
      }
    } else if (passage != null) {
      found = find(new BioCPassageIterator(passage));
      if (!found) {
        found = find(new BioCSentenceIterator(passage));
      }
    } else if (sentence != null) {
      found = find(new BioCSentenceIterator(sentence));
    }
    return found;
  }

  private boolean find(BioCSentenceIterator itr) {
    while (itr.hasNext()) {
      BioCSentence sentence = itr.next();
      annotation = sentence.getAnnotation(annotationID);
      if (annotation != null) {
        container = sentence;
        return true;
      }
    }
    return false;
  }

  private boolean find(BioCPassageIterator itr) {
    while (itr.hasNext()) {
      BioCPassage passage = itr.next();
      annotation = passage.getAnnotation(annotationID);
      if (annotation != null) {
        container = passage;
        return true;
      }
    }
    return false;
  }

  private boolean find(BioCDocumentIterator itr) {
    while (itr.hasNext()) {
      BioCDocument document = itr.next();
      annotation = document.getAnnotation(annotationID);
      if (annotation != null) {
        container = document;
        return true;
      }
    }
    return false;
  }

  public BioCAnnotation getAnnotation() {
    return annotation;
  }

  public boolean isAnnotationFromDocument() {
    return container instanceof BioCDocument;
  }

  public boolean isAnnotationFromPassage() {
    return container instanceof BioCPassage;
  }

  public boolean isAnnotationFromSentence() {
    return container instanceof BioCSentence;
  }

  public BioCDocument asDocument() {
    return (BioCDocument) container;
  }

  public BioCPassage asPassage() {
    return (BioCPassage) container;
  }

  public BioCSentence asSentence() {
    return (BioCSentence) container;
  }
}
