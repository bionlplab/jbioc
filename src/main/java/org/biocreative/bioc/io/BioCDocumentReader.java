package org.biocreative.bioc.io;

import java.io.Closeable;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;

/**
 * The interface allows forward, read-only access to BioC file. It is designed
 * to sequentially read the BioC file into document every time the method
 * readDocument is called.
 */
public interface BioCDocumentReader extends Closeable, Iterable<BioCDocument> {

  /**
   * Read a BioCDocument from the XML file
   */
  public BioCDocument readDocument()
      throws XMLStreamException;

  /**
   * Read the collection information: source, date, key, infons
   */
  public BioCCollection readCollectionInfo()
      throws XMLStreamException;

  /**
   * Returns the absolute URI of the BioC DTD file.
   */
  public String getDTD();
}
