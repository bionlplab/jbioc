package com.pengyifan.bioc.util;

import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.BioCRelation;
import com.pengyifan.bioc.BioCSentence;

import java.util.Collections;
import java.util.Iterator;

/**
 * A relation iterator over a collection, a document, a passage or a single sentence.
 * 
 * @since 1.0.0
 * @see com.pengyifan.bioc.util.BioCDocumentIterator
 * @see com.pengyifan.bioc.util.BioCPassageIterator
 * @see com.pengyifan.bioc.util.BioCSentenceIterator
 * @author Yifan Peng
 */
public class BioCRelationIterator implements Iterator<BioCRelation>, BioCIterator<BioCRelation> {

  private Iterator<BioCRelation> relationItr;

  private BioCDocumentIterator documentItr;
  private BioCPassageIterator passageItr;
  private BioCSentenceIterator sentenceItr;
  private BioCRelation currentRelation;

  /**
   * Creates an empty iterator.
   */
  public BioCRelationIterator() {
    documentItr = new BioCDocumentIterator();
    passageItr = new BioCPassageIterator();
    sentenceItr = new BioCSentenceIterator();
    relationItr = Collections.emptyIterator();
  }

  /**
   * Creates an iterator given the BioC collection.
   * 
   * @param collection the BioC collection
   */
  public BioCRelationIterator(BioCCollection collection) {
    documentItr = new BioCDocumentIterator(collection);
    passageItr = new BioCPassageIterator(collection);
    sentenceItr = new BioCSentenceIterator(collection);
    relationItr = Collections.emptyIterator();
  }

  /**
   * Creates an iterator given the BioC document.
   * 
   * @param document the BioC document
   */
  public BioCRelationIterator(BioCDocument document) {
    documentItr = new BioCDocumentIterator(document);
    passageItr = new BioCPassageIterator(document);
    sentenceItr = new BioCSentenceIterator(document);
    relationItr = Collections.emptyIterator();
  }

  /**
   * Creates an iterator given the BioC passage.
   * 
   * @param passage the BioC passage
   */
  public BioCRelationIterator(BioCPassage passage) {
    documentItr = new BioCDocumentIterator();
    passageItr = new BioCPassageIterator(passage);
    sentenceItr = new BioCSentenceIterator(passage);
    relationItr = Collections.emptyIterator();
  }

  /**
   * Creates an iterator given the BioC sentence.
   * 
   * @param sentence the BioC sentence
   */
  public BioCRelationIterator(BioCSentence sentence) {
    documentItr = new BioCDocumentIterator();
    passageItr = new BioCPassageIterator();
    sentenceItr = new BioCSentenceIterator(sentence);
    relationItr = Collections.emptyIterator();
  }

  @Override
  public boolean hasNext() {
    if (relationItr.hasNext()) {
      return true;
    } else if (documentItr.hasNext()) {
      BioCDocument doc = documentItr.next();
      relationItr = doc.getRelations().iterator();
      return hasNext();
    } else if (passageItr.hasNext()) {
      BioCPassage passage = passageItr.next();
      relationItr = passage.getRelations().iterator();
      return hasNext();
    } else if (sentenceItr.hasNext()) {
      BioCSentence sentence = sentenceItr.next();
      relationItr = sentence.getRelations().iterator();
      return hasNext();
    } else {
      return false;
    }
  }

  @Override
  public BioCRelation next() {
    currentRelation = relationItr.next();
    return currentRelation;
  }

  @Override
  public BioCRelation current() {
    if (currentRelation == null) {
      throw new IllegalStateException();
    }
    return currentRelation;
  }
}
