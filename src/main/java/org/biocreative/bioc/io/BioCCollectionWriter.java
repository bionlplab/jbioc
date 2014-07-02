package org.biocreative.bioc.io;

import java.io.Closeable;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;

public interface BioCCollectionWriter extends Closeable {

  public void writeCollection(BioCCollection collection)
      throws XMLStreamException;

  public void setDTD(String dtd);
}
