package com.pengyifan.bioc.util;

import com.pengyifan.bioc.BioCAnnotation;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.BioCSentence;

import java.util.Collections;
import java.util.Iterator;

/**
 * An annotation iterator over a collection, a document, a passage or a single
 * sentence.
 *
 * @author Yifan Peng
 * @see com.pengyifan.bioc.util.BioCDocumentIterator
 * @see com.pengyifan.bioc.util.BioCPassageIterator
 * @see com.pengyifan.bioc.util.BioCSentenceIterator
 * @since 1.0.0
 */
public class BioCAnnotationIterator implements BioCIterator<BioCAnnotation> {

  private Iterator<BioCAnnotation> annotationItr;

  private BioCDocumentIterator documentItr;
  private BioCPassageIterator passageItr;
  private BioCSentenceIterator sentenceItr;
  private BioCAnnotation currentAnnotation;

  /**
   * Creates an empty iterator.
   */
  public BioCAnnotationIterator() {
    documentItr = new BioCDocumentIterator();
    passageItr = new BioCPassageIterator();
    sentenceItr = new BioCSentenceIterator();
    annotationItr = Collections.emptyIterator();
  }

  /**
   * Creates an iterator given the BioC collection.
   *
   * @param collection the BioC collection
   */
  public BioCAnnotationIterator(BioCCollection collection) {
    documentItr = new BioCDocumentIterator(collection);
    passageItr = new BioCPassageIterator(collection);
    sentenceItr = new BioCSentenceIterator(collection);
    annotationItr = Collections.emptyIterator();
  }

  /**
   * Creates an iterator given the BioC document.
   *
   * @param document the BioC document
   */
  public BioCAnnotationIterator(BioCDocument document) {
    documentItr = new BioCDocumentIterator(document);
    passageItr = new BioCPassageIterator(document);
    sentenceItr = new BioCSentenceIterator(document);
    annotationItr = Collections.emptyIterator();
  }

  /**
   * Creates an iterator given the BioC passage.
   *
   * @param passage the BioC passage
   */
  public BioCAnnotationIterator(BioCPassage passage) {
    documentItr = new BioCDocumentIterator();
    passageItr = new BioCPassageIterator(passage);
    sentenceItr = new BioCSentenceIterator(passage);
    annotationItr = Collections.emptyIterator();
  }

  /**
   * Creates an iterator given the BioC sentence.
   *
   * @param sentence the BioC sentence
   */
  public BioCAnnotationIterator(BioCSentence sentence) {
    documentItr = new BioCDocumentIterator();
    passageItr = new BioCPassageIterator();
    sentenceItr = new BioCSentenceIterator(sentence);
    annotationItr = Collections.emptyIterator();
  }

  @Override
  public boolean hasNext() {
    if (annotationItr.hasNext()) {
      return true;
    } else if (documentItr.hasNext()) {
      BioCDocument doc = documentItr.next();
      annotationItr = doc.getAnnotations().iterator();
      return hasNext();
    } else if (passageItr.hasNext()) {
      BioCPassage passage = passageItr.next();
      annotationItr = passage.getAnnotations().iterator();
      return hasNext();
    } else if (sentenceItr.hasNext()) {
      BioCSentence sentence = sentenceItr.next();
      annotationItr = sentence.getAnnotations().iterator();
      return hasNext();
    } else {
      return false;
    }
  }

  @Override
  public BioCAnnotation next() {
    currentAnnotation = annotationItr.next();
    return currentAnnotation;
  }

  @Override
  public BioCAnnotation current() {
    if (currentAnnotation == null) {
      throw new IllegalStateException();
    }
    return currentAnnotation;
  }
}
