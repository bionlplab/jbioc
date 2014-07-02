package org.biocreative.bioc.io;

import java.io.Closeable;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;

public interface BioCCollectionReader extends Closeable {

  public BioCCollection readCollection()
      throws XMLStreamException;
}
