package com.pengyifan.bioc.util;

import com.pengyifan.bioc.BioCAnnotation;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCLocation;
import com.pengyifan.bioc.BioCNode;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.BioCRelation;
import com.pengyifan.bioc.BioCSentence;
import com.pengyifan.bioc.BioCStructure;
import com.pengyifan.bioc.io.BioCDocumentReader;
import com.pengyifan.bioc.validation.BioCErrorHandler;
import com.pengyifan.bioc.validation.BioCValidator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.SAXException;

import static com.pengyifan.bioc.util.BioCLog.log;

public class BioCValidate3 implements BioCValidator {

  private BioCErrorHandlerImpl errorHandler;

  public BioCValidate3(boolean throwException) {
    this(throwException, System.err);
  }

  public BioCValidate3(boolean throwException, PrintStream ps) {
    errorHandler = new BioCErrorHandlerImpl();
    errorHandler.ps = ps;
    errorHandler.throwException = throwException;
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
   * Checks text, annotations and relations of the document.
   *
   * @param document input document
   */
  public void check(BioCDocument document) {
    String text = checkText(document);
    check(document, 0, text);
    for (BioCPassage passage : document.getPassages()) {
      check(passage, 0, text, document);
      for (BioCSentence sentence : passage.getSentences()) {
        check(sentence, 0, text, document, passage);
      }
    }
  }

  /**
   * Checks text, annotations and relations of the passage.
   *
   * @param passage input passage
   */
  public void check(BioCPassage passage) {
    String text = checkText(passage);
    check(passage, passage.getOffset(), text);
    for (BioCSentence sentence : passage.getSentences()) {
      check(sentence, 0, text, passage);
    }
  }

  /**
   * Checks text, annotations and relations of the sentence.
   *
   * @param sentence input sentence
   */
  public void check(BioCSentence sentence) {
    String text = checkText(sentence);
    check(sentence, sentence.getOffset(), text);
  }

  /**
   * Check the annotation and relation in the structure.
   *
   * @param structure the specified structure
   * @param offset    the offset of this structure
   * @param text      the text that annotations should match
   * @param parents   the path from root until this structure (not included
   */
  public void check(BioCStructure structure, int offset, String text, BioCStructure... parents) {
    BioCStructure[] path = new BioCStructure[parents.length + 1];
    System.arraycopy(parents, 0, path, 0, parents.length);
    path[path.length - 1] = structure;
    String location = log(path);
    checkAnnotations(structure, offset, text, location);
    checkRelations(structure, location);
  }

  private void checkAnnotations(BioCStructure structure, int offset, String text, String location) {
    for (BioCAnnotation annotation : structure.getAnnotations()) {
      if (!annotation.getText().isPresent()) {
        errorHandler.error("The %s in %s has no text", log(annotation), location);
      }

      BioCLocation total = annotation.getTotalLocation();
      String expected = text.substring(
          total.getOffset() - offset, total.getOffset() + total.getLength() - offset);
      String actual = annotation.getText().get();
      if (!expected.equals(actual)) {
        errorHandler.error("The %s text in %s is incorrect.\n" +
                "  Expected : %s\n" +
                "  Actual   : %s",
            log(annotation), location, expected, actual);
      }
    }
  }

  private void checkRelations(BioCStructure structure, String location) {
    for (BioCRelation relation : structure.getRelations()) {
      for (BioCNode node : relation.getNodes()) {
        if (!structure.getAnnotation(node.getRefid()).isPresent()) {
          errorHandler.error("Cannot find %s in %s in %s", log(node), log(relation), location);
        }
      }
    }
  }

  public String checkText(BioCDocument document) {
    StringBuilder sb = new StringBuilder();
    for (BioCPassage passage : document.getPassages()) {
      if (sb.length() > passage.getOffset()) {
        haveToThrow("The %s is overlapped with previous text.", log(document, passage));
      }
      fillNewLine(sb, passage.getOffset());
      sb.append(checkText(passage));
    }
    return sb.toString();
  }

  public String checkText(BioCPassage passage) {
    if (passage.getText().isPresent() && !passage.getText().get().isEmpty()) {
      if (passage.getSentenceCount() == 0) {
        return passage.getText().get();
      } else {
        errorHandler.error("The %s contains both text and sentences.", log(passage));
      }
    }

    StringBuilder sb = new StringBuilder();
    for (BioCSentence sentence : passage.getSentences()) {
      if (passage.getOffset() + sb.length() > sentence.getOffset()) {
        haveToThrow("The %s is overlapped with previous text.", log(passage, sentence));
      }
      fillNewLine(sb, sentence.getOffset() - passage.getOffset());
      sb.append(checkText(sentence));
    }
    return sb.toString();
  }

  public String checkText(BioCSentence sentence) {
    if (!sentence.getText().isPresent()) {
      errorHandler.error("The %s has no text.", log(sentence));
    }
    return sentence.getText().get();
  }

  private StringBuilder fillNewLine(StringBuilder sb, int offset) {
    return sb.append(StringUtils.repeat('\n', offset - sb.length()));
  }

  private void haveToThrow(String format, Object... objects) {
    throw new IllegalArgumentException(String.format(format, objects));
  }

  public void setPrintStream(PrintStream ps) {
    errorHandler.ps = ps;
  }

  public void setThrowException(boolean throwException) {
    errorHandler.throwException = throwException;
  }

  @Override
  public void validate(File file) {
    try {
      BioCDocumentReader reader = new BioCDocumentReader(file);
      BioCDocument document = null;
      while ((document = reader.readDocument()) != null) {
        checkText(document);
      }
      reader.close();
    } catch (IOException e) {
      errorHandler.error(e);
    } catch (XMLStreamException e) {
      errorHandler.error(e);
    }
  }

  @Override
  public BioCErrorHandler getErrorHandler() {
    return errorHandler;
  }

  @Override
  public void setErrorHandler(BioCErrorHandler errorHandler) {
    throw new UnsupportedOperationException();
  }

  private static class BioCErrorHandlerImpl implements BioCErrorHandler {

    PrintStream ps;
    boolean throwException;

    @Override
    public void error(IOException e) {
      error(e.getLocalizedMessage());
    }

    @Override
    public void error(SAXException e) {
      error(e.getLocalizedMessage());
    }

    @Override
    public void error(TransformerException e) {
      error(e.getLocalizedMessage());
    }

    @Override
    public void error(XMLStreamException e) {
      error(e.getLocalizedMessage());
    }

    @Override
    public void error(IllegalArgumentException e) {
      error(e.getLocalizedMessage());
    }

    private void error(String format, Object... objects) {
      if (throwException) {
        throw new IllegalArgumentException(String.format(format, objects));
      } else {
        ps.printf(format, objects);
        ps.println();
      }
    }
  }
}
