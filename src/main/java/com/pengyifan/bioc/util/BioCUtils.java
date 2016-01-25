package com.pengyifan.bioc.util;

import com.google.common.collect.Lists;
import com.pengyifan.bioc.BioCAnnotation;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.BioCRelation;
import com.pengyifan.bioc.BioCSentence;
import com.pengyifan.bioc.io.BioCCollectionReader;
import com.pengyifan.bioc.io.BioCCollectionWriter;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class BioCUtils {
  public static final BioCCollection readCollection(Path file)
      throws IOException, XMLStreamException {
    BioCCollectionReader reader = new BioCCollectionReader(Files.newBufferedReader(file));
    BioCCollection collection = reader.readCollection();
    reader.close();
    return collection;
  }

  public static final void writeCollectioin(Path file, BioCCollection collection)
      throws IOException, XMLStreamException {
    BioCCollectionWriter writer = new BioCCollectionWriter(Files.newBufferedWriter(file));
    writer.writeCollection(collection);
    writer.close();
  }

  public static final String getXPathString(BioCDocument document) {
    return String.format("collection/document[id=%s]", document.getID());
  }

  public static final String getXPathString(BioCDocument document, BioCPassage passage) {
    return String.format("%s/passage[offset=%d]",
        getXPathString(document), passage.getOffset());
  }

  public static final String getXPathString(BioCDocument document, BioCPassage passage,
      BioCSentence sentence) {
    return String.format("%s/sentence[offset=%d]",
        getXPathString(document, passage), sentence.getOffset());
  }

  public static final String getXPathString(BioCDocument document, BioCPassage passage,
      BioCSentence sentence, BioCAnnotation annotation) {
    return String.format("%s/annotation[id=%s]",
        getXPathString(document, passage, sentence), annotation.getID());
  }

  public static final String getXPathString(BioCDocument document, BioCPassage passage,
      BioCAnnotation annotation) {
    return String.format("%s/annotation[id=%s]",
        getXPathString(document, passage), annotation.getID());
  }

  public static final String getXPathString(BioCDocument document, BioCPassage passage,
      BioCRelation relation) {
    return String.format("%s/relation[id=%s]",
        getXPathString(document, passage), relation.getID());
  }

  public static final List<BioCSentence> getSentences(BioCCollection collection) {
    return Lists.newArrayList(new BioCSentenceIterator(collection));
  }

  private static StringBuilder fillText(StringBuilder sb, int offset) {
    while (sb.length() < offset) {
      sb.append('\n');
    }
    return sb;
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
    if (passage.getText().isPresent() && !passage.getText().get().isEmpty()) {
      return passage.getText().get();
    }

    StringBuilder sb = new StringBuilder();
    for (BioCSentence sentence : passage.getSentences()) {
      fillText(sb, sentence.getOffset() - passage.getOffset());
      checkArgument(sentence.getText().isPresent(), "BioC sentence has no text");
      sb.append(sentence.getText().get());
    }
    return sb.toString();
  }
}
