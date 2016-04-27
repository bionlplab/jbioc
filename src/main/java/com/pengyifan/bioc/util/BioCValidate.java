package com.pengyifan.bioc.util;

import com.pengyifan.bioc.BioCAnnotation;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCNode;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.BioCRelation;
import com.pengyifan.bioc.BioCSentence;

import java.util.Collection;
import java.util.StringJoiner;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @deprecated Use {@code BioCValidate2} instead.
 */
public class BioCValidate {

  private static boolean NonStop = false;

  public static void setNonStop(boolean nonStop) {
    NonStop = nonStop;
  }

  /**
   * Checks the annotations and ie.
   *
   * @param sentence input sentence
   */
  public static void check(BioCSentence sentence) {
    // check annotation offset and text
    checkAnnotations(sentence.getAnnotations(), sentence.getText().get(), sentence.getOffset());
    // check relation
    for (BioCRelation relation : sentence.getRelations()) {
      for (BioCNode node : relation.getNodes()) {
        checkArgument(sentence.getAnnotation(node.getRefid()).isPresent(),
            "Cannot find node %s in relation %s", node, relation);
      }
    }
  }

  /**
   * If the passage has text, checks annotations and ie; otherwise directly returns.
   *
   * @param passage input passage
   */
  public static void check(BioCPassage passage) {
    for (BioCSentence sentence : passage.getSentences()) {
      check(sentence);
    }

    String text = getText(passage);

    // check annotation offset and text
    checkAnnotations(passage.getAnnotations(), text, passage.getOffset());
    // check relation
    for (BioCRelation relation : passage.getRelations()) {
      for (BioCNode node : relation.getNodes()) {
        checkArgument(passage.getAnnotation(node.getRefid()).isPresent(),
            "Cannot find node %s in relation %s", node, relation);
      }
    }
  }

  /**
   * Checks annotations and ie.
   *
   * @param document input document
   */
  public static void check(BioCDocument document) {
    for (BioCPassage passage : document.getPassages()) {
      check(passage);
    }

    String text = getText(document);
    // check annotation offset and text
    checkAnnotations(document.getAnnotations(), text, 0);
    // check relation
    for (BioCRelation relation : document.getRelations()) {
      for (BioCNode node : relation.getNodes()) {
        checkArgument(document.getAnnotation(node.getRefid()).isPresent(),
            "Cannot find node %s in relation %s", node, relation);
      }
    }
  }

  /**
   * Checks annotations and ie.
   *
   * @param collection input collection
   */
  public static void check(BioCCollection collection) {
    for (BioCDocument document: collection.getDocuments()) {
      check(document);
    }
  }

  public static String getText(BioCDocument document) {
    StringBuilder sb = new StringBuilder();
    for (BioCPassage passage : document.getPassages()) {
      fillText(sb, passage.getOffset());
      sb.append(getText(passage));
    }
    return sb.toString();
  }

  public static String getText(BioCPassage passage) {
    StringBuilder sb = new StringBuilder();
    if (passage.getText().isPresent() && !passage.getText().get().isEmpty()) {
      sb.append(passage.getText().get());
    } else {
      for (BioCSentence sentence : passage.getSentences()) {
        fillText(sb, sentence.getOffset() - passage.getOffset());
        checkArgument(sentence.getText().isPresent(), "BioC sentence has no text");
        sb.append(sentence.getText().get());
      }
    }
    return sb.toString();
  }

  public static void checkAnnotations(Collection<BioCAnnotation> annotations,
      String text, int offset) {
    for (BioCAnnotation annotation : annotations) {
      StringJoiner sj = new StringJoiner(" ");
      annotation.getLocations().stream()
          .sorted((l1, l2) -> Integer.compare(l1.getOffset(), l2.getOffset()))
          .forEach(
              l -> {
                String substring = text.substring(
                    l.getOffset() - offset,
                    l.getOffset() + l.getLength() - offset
                );
                sj.add(substring);
              }
          );
      try {
        checkArgument(sj.toString().equals(annotation.getText().get()),
            "Annotation text is incorrect: %s\n" +
                "Annotation : %s\n" +
                "Actual text: %s",
            annotation, sj.toString());
      } catch (IllegalArgumentException e) {
        if (NonStop) {
          System.err.println(e.getMessage());
        } else {
          throw  e;
        }
      }
    }
  }

  private static StringBuilder fillText(StringBuilder sb, int offset) {
    while (sb.length() < offset) {
      sb.append('\n');
    }
    return sb;
  }
}
