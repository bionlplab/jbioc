package com.pengyifan.bioc.util;

import com.google.common.collect.Maps;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCSentence;
import org.javatuples.Pair;

import java.util.Map;
import java.util.Optional;

public class BioCSearch {

  /**
   * (doc id, sentence offset) --> sentence
   */
  private Map<Pair<String, Integer>, BioCSentence> sentences;

  public BioCSearch(BioCCollection collection) {
    sentences = Maps.newHashMap();
    BioCSentenceIterator itr = new BioCSentenceIterator(collection);
    while (itr.hasNext()) {
      BioCSentence sentence = itr.next();
      BioCDocument document = itr.getDocument();
      Pair<String, Integer> key = Pair.with(document.getID(), sentence.getOffset());
      sentences.put(key, sentence);
    }
  }

  public Optional<BioCSentence> getSentence(String docId, int sentenceOffset) {
    Pair<String, Integer> key = Pair.with(docId, sentenceOffset);
    return Optional.ofNullable(sentences.get(key));
  }
}
