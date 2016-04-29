package com.pengyifan.bioc.util;

import com.google.common.collect.Lists;
import com.pengyifan.bioc.BioCAnnotation;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCPassage;
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

  private BioCUtils(){}

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

  public static final String getXPathString(BioCDocument document, BioCPassage passage,
      BioCSentence sentence) {
    return BioCLog.log(document, passage, sentence);
  }

  public static final List<BioCSentence> getSentences(BioCCollection collection) {
    return Lists.newArrayList(new BioCSentenceIterator(collection));
  }

  public static String getText(BioCDocument document) {
    return new BioCValidate3(false).checkText(document);
  }

  public static String getText(BioCPassage passage) {
    return new BioCValidate3(false).checkText(passage);
  }
}
