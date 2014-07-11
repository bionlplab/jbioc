package org.biocreative.bioc.io;

import java.io.Closeable;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;

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
  
//  /**
//   * Reset the stream to the most recent mark.
//   */
//  public void reset();
}
