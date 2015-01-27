package org.biocreative.bioc.io;

import java.io.Reader;
import java.io.Writer;

import javax.xml.stream.XMLStreamException;

public interface BioCXMLStrategy {

  /**
   * Creates a BioCCollectionWriter instance using the given writer.
   */
  public BioCCollectionWriter createBioCCollectionWriter(Writer out)
      throws XMLStreamException;

  /**
   * Creates a BioCDocumentWriter instance using the given writer.
   */
  public BioCDocumentWriter createBioCDocumentWriter(Writer out)
      throws XMLStreamException;

  /**
   * Creates a BioCCollectionReader instance using the given reader.
   */
  public BioCCollectionReader createBioCCollectionReader(Reader in)
      throws XMLStreamException;

  /**
   * Creates a BioCDocumentReader instance using the given reader.
   */
  public BioCDocumentReader createBioCDocumentReader(Reader in)
      throws XMLStreamException;
}
