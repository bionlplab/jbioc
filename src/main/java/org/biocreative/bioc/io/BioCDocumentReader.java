package org.biocreative.bioc.io;

import java.io.Closeable;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;

public interface BioCDocumentReader extends Closeable, Iterable<BioCDocument> {

  public BioCDocument readDocument()
      throws XMLStreamException;

  public BioCCollection readCollectionInfo()
      throws XMLStreamException;
}
