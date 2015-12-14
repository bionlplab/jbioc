package com.pengyifan.bioc.util;

import com.pengyifan.bioc.BioCAnnotation;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCLocation;
import com.pengyifan.bioc.BioCNode;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.BioCRelation;
import com.pengyifan.bioc.BioCSentence;

import static com.google.common.base.Preconditions.checkArgument;

public class BioCValidate {

  /**
   * Checks the annotations and relations.
   *
   * @param sentence input sentence
   */
  public static void check(BioCSentence sentence) {
    // check annotation offset and text
    for(BioCAnnotation annotation: sentence.getAnnotations()) {
      for(BioCLocation location: annotation.getLocations()) {
        String substring = sentence.getText().get().substring(
            location.getOffset() - sentence.getOffset(),
            location.getOffset() + location.getLength() - sentence.getOffset()
        );
        checkArgument(substring.equals(annotation.getText().get()),
            "Annotation text is incorrect.\n" +
                "Annotation:  %s\n" +
                "Actual text: %s\n" +
                "Sentence:    %s", annotation, substring, sentence);
      }
    }
    // check relation
    for(BioCRelation relation: sentence.getRelations()) {
      for(BioCNode node: relation.getNodes()) {
        checkArgument(sentence.getAnnotation(node.getRefid()).isPresent(),
            "Cannot find node %s in relation %s", node, relation);
      }
    }
  }

  /**
   * If the passage has text, checks annotations and relations; otherwise directly returns.
   *
   * @param passage input passage
   */
  public static void check(BioCPassage passage) {
    if (!passage.getText().isPresent()) {
      return;
    }
    // check annotation offset and text
    for(BioCAnnotation annotation: passage.getAnnotations()) {
      for(BioCLocation location: annotation.getLocations()) {
        String substring = passage.getText().get().substring(
            location.getOffset() - passage.getOffset(),
            location.getOffset() + location.getLength() - passage.getOffset()
        );
        checkArgument(substring.equals(annotation.getText().get()),
            "Annotation text is incorrect.\n" +
                "Annotation:  %s\n" +
                "Actual text: %s", annotation, substring);
      }
    }
    // check relation
    for(BioCRelation relation: passage.getRelations()) {
      for(BioCNode node: relation.getNodes()) {
        checkArgument(passage.getAnnotation(node.getRefid()).isPresent(),
            "Cannot find node %s in relation %s", node, relation);
      }
    }
  }

  /**
   * Checks annotations and relations.
   *
   * @param collection input collection
   */
  public static void check(BioCCollection collection) {
    BioCPassageIterator iterator = new BioCPassageIterator(collection);
    while (iterator.hasNext()) {
      BioCValidate.check(iterator.next());
    }
  }
}
