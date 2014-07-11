package org.biocreative.bioc.io;

import java.io.Reader;
import java.io.Writer;

import javax.xml.stream.XMLStreamException;


public interface BioCXMLStrategy {

  public BioCCollectionWriter createBioCCollectionWriter(Writer out)
      throws XMLStreamException;

  public BioCDocumentWriter createBioCDocumentWriter(Writer out)
      throws XMLStreamException;

  public BioCCollectionReader createBioCCollectionReader(Reader in)
      throws XMLStreamException;

  public BioCDocumentReader createBioCDocumentReader(Reader in)
      throws XMLStreamException;
}
