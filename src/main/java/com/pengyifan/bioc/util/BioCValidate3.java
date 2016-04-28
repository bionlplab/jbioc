package com.pengyifan.bioc.util;

import com.pengyifan.bioc.BioCAnnotation;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCLocation;
import com.pengyifan.bioc.BioCNode;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.BioCRelation;
import com.pengyifan.bioc.BioCSentence;
import java.io.PrintStream;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class BioCValidate3 {

  private PrintStream ps;
  private boolean isInterrupted;

  public BioCValidate3() {
    ps = System.err;
    isInterrupted = false;
  }

  public void setPrintStream(PrintStream ps) {
    this.ps = ps;
  }

  public void setInterrupted(boolean interrupted) {
    isInterrupted = interrupted;
  }

  /**
   * Checks annotations and ie.
   *
   * @param collection input collection
   */
  public void check(BioCCollection collection) {
    for (BioCDocument document : collection.getDocuments()) {
      check(document);
    }
  }

  /**
   * Checks annotations and ie.
   *
   * @param document input document
   */
  public void check(BioCDocument document) {
    String text = checkText(document);

    // check annotation offset
    BioCAnnotationIterator annItr = new BioCAnnotationIterator(document);
    checkAnnotations(annItr, text, 0);

    // check relation
    for (BioCRelation relation : document.getRelations()) {
      for (BioCNode node : relation.getNodes()) {
        if (!document.getAnnotation(node.getRefid()).isPresent()) {
          error("Cannot find node %s in relation %s\nLocation: %s",
              node, relation, BioCUtils.getXPathString(document));
        }
      }
    }

    // check passage
    for (BioCPassage passage : document.getPassages()) {
      for (BioCRelation relation : passage.getRelations()) {
        for (BioCNode node : relation.getNodes()) {
          if (!passage.getAnnotation(node.getRefid()).isPresent()) {
            error("Cannot find node %s in relation %s\nLocation: %s",
                node, relation, BioCUtils.getXPathString(document, passage));
          }
        }
      }

      // check sentence
      for (BioCSentence sentence : passage.getSentences()) {
        for (BioCRelation relation : sentence.getRelations()) {
          for (BioCNode node : relation.getNodes()) {
            if (!sentence.getAnnotation(node.getRefid()).isPresent()) {
              error("Cannot find node %s in relation %s\nLocation: %s",
                  node, relation, BioCUtils.getXPathString(document, passage, sentence));
            }
          }
        }
      }
    }
  }

  private void checkAnnotations(BioCAnnotationIterator annItr, String text, int offset) {
    while (annItr.hasNext()) {
      BioCAnnotation annotation = annItr.next();
      if (!annotation.getText().isPresent()) {
        error("The annotation has no text: %s\n", annotation);
      }

      BioCLocation total = annotation.getTotalLocation();
      for (BioCLocation location: annotation.getLocations()) {
        String expected = text.substring(
            location.getOffset() - offset,
            location.getOffset() + location.getLength() - offset);
        String actual = annotation.getText().get().substring(
            location.getOffset() - total.getOffset(),
            location.getOffset() + location.getLength() - total.getOffset());
        if (!expected.equals(actual)) {
          error("Annotation text is incorrect.\n" +
                  "  Annotation : %s\n" +
                  "  Actual text: %s\n" +
                  "  %s",
              annotation, actual, annotation);
        }
      }
    }
  }

  private String checkText(BioCDocument document) {
    StringBuilder sb = new StringBuilder();
    for (BioCPassage passage : document.getPassages()) {
      if (sb.length() > passage.getOffset()) {
        error("The passage is overlapped with previous text.\n%s\n", passage);
      }
      fillNewLine(sb, passage.getOffset());
      sb.append(checkText(passage));
    }
    return sb.toString();
  }

  private String checkText(BioCPassage passage) {
    if (passage.getText().isPresent() && !passage.getText().get().isEmpty()) {
      if (passage.getSentenceCount() == 0) {
        return passage.getText().get();
      } else {
        error("The passage contains both text and sentences.\n", passage);
      }
    }

    StringBuilder sb = new StringBuilder();
    for (BioCSentence sentence : passage.getSentences()) {
      if (!sentence.getText().isPresent()) {
        error("The sentence has no text.\n", sentence);
      } else if (passage.getOffset() + sb.length() > sentence.getOffset()) {
        error("The sentence is overlapped with previous text.\n%s\n", sentence);
      }
      fillNewLine(sb, sentence.getOffset() - passage.getOffset());
      sb.append(sentence.getText().get());
    }
    return sb.toString();
  }

  private StringBuilder fillNewLine(StringBuilder sb, int offset) {
    return sb.append(StringUtils.repeat('\n', offset - sb.length()));
  }

  private void error(String format, Object ... objects) {
    if (isInterrupted) {
      throw new IllegalArgumentException(String.format(format, objects));
    } else {
      ps.printf(format, objects);
    }
  }
}
