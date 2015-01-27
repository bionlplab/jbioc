package org.biocreative.bioc.io;

import java.io.Closeable;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;

/**
 * The interface specifies how to write the BioC file. The users need to set
 * all attributes before, then calls writeCollection once to write the whole
 * BioC collection. Method close is required at the end to finish the writing
 * task and free any resources associated with the writer.
 */
public interface BioCCollectionWriter extends Closeable {

  /**
   * Writes the whole BioC collection into the xml file. This method can only
   * be called once.
   */
  public void writeCollection(BioCCollection collection)
      throws XMLStreamException;

  /**
   * Sets the absolute URI of the BioC DTD file.
   */
  public void setDTD(String dtd);

  /**
   * Returns the absolute URI of the BioC DTD file.
   */
  public String getDTD();

  /**
   * Returns the charset encoding of the BioC file.
   */
  public String getEncoding();

  /**
   * Sets the charset encoding of the BioC file.
   */
  public void setEncoding(String encoding);

  /**
   * Gets the xml version declared on the xml declaration. Returns null if none
   * was declared
   */
  public String getVersion();

  /**
   * Sets the xml version declared on the xml declaration.
   */
  public void setVersion(String version);

  /**
   * Gets the standalone declaration from the xml declaration
   */
  public boolean isStandalone();

  /**
   * Sets the standalone declaration to the xml declaration
   */
  public void setStandalone(boolean standalone);
}
