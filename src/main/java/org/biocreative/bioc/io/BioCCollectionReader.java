package org.biocreative.bioc.io;

import java.io.Closeable;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;

/**
 * The interface allows forward, read-only access to BioC file. It is designed
 * to read the entire BioC file into one collection.
 */
public interface BioCCollectionReader extends Closeable {

  /**
   * Returns the absolute URI of the BioC DTD file.
   */
  public String getDTD();

  /**
   * Returns the collection of documents.
   */
  public BioCCollection readCollection()
      throws XMLStreamException;
}
