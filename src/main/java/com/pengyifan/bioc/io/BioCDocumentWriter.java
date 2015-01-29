package com.pengyifan.bioc.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.lang3.Validate;

import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;

/**
 * The class specifies how to write the BioC file. The users need to set all
 * attributes before, then calls writeBeginCollectionInfo to set the collection
 * level's information such as source, date, and key. Then users can call
 * writeDocument repeatedly to sequentially write BioC documents into the file.
 * Method close is required at the end to finish the writing task and free any
 * resources associated with the writer.
 */
public class BioCDocumentWriter implements Closeable {

  private BioCWriter writer;
  private String dtd;
  private String encoding;
  private String version;
  private boolean standalone;
  private boolean hasWrittenCollectionInfo;

  public BioCDocumentWriter(Writer out)
      throws FactoryConfigurationError, XMLStreamException {
    writer = new BioCWriter(out);
    hasWrittenCollectionInfo = false;
    encoding = "UTF-8";
    version = "1.0";
    standalone = true;
  }

  @Override
  public final void close()
      throws IOException {
    try {
      // end collection
      writer.writeEndCollection()
          .writeEndDocument()
          .close();
    } catch (XMLStreamException e) {
      throw new IOException(e.getMessage());
    }
  }

  /**
   * Returns the absolute URI of the BioC DTD file.
   * 
   * @return the absolute URI of the BioC DTD file
   */
  public String getDTD() {
    return dtd;
  }

  /**
   * Returns the charset encoding of the BioC file.
   * 
   * @return the charset encoding of the BioC file
   */
  public String getEncoding() {
    return encoding;
  }

  /**
   * Gets the XML version declared on the XML declaration. Returns null if none
   * was declared
   * 
   * @return the XML version declared on the XML declaration
   */
  public String getVersion() {
    return version;
  }

  /**
   * Gets the standalone declaration from the XML declaration.
   * 
   * @return true if the DTD is ignored by the parser.
   */
  public boolean isStandalone() {
    return standalone;
  }

  /**
   * Sets the absolute URI of the BioC DTD file.
   * 
   * @param dtd the absolute URI of the BioC DTD file
   */
  public void setDTD(String dtd) {
    this.dtd = dtd;
  }

  /**
   * Sets the charset encoding of the BioC file.
   * 
   * @param encoding the charset encoding of the BioC file
   */
  public void setEncoding(String encoding) {
    Validate.notNull(encoding);
    this.encoding = encoding;
  }

  /**
   * Sets the standalone declaration to the XML declaration.
   * 
   * @param standalone true if the parser can ignore the DTD
   */
  public void setStandalone(boolean standalone) {
    this.standalone = standalone;
  }

  /**
   * Sets the XML version declared on the XML declaration.
   * 
   * @param version the XML version declared on the XML declaration
   */
  public void setVersion(String version) {
    Validate.notNull(version);
    this.version = version;
  }

  /**
   * Writes the BioC collection information: source, date, key, infons, etc.
   * 
   * @param collection the BioC collection whose information will be written
   * @throws XMLStreamException unexpected processing errors
   */
  public void writeBeginCollectionInfo(BioCCollection collection)
      throws XMLStreamException {

    if (hasWrittenCollectionInfo) {
      throw new IllegalStateException(
          "writeCollectionInfo can only be invoked once.");
    }

    hasWrittenCollectionInfo = true;

    Validate.notNull(dtd, "haven't set DTD yet");

    writer
        .writeStartDocument(encoding, version, standalone)
        .writeDTD(dtd)
        .writeBeginCollectionInfo(collection);
  }

  /**
   * Writes the BioC document into the XML file. This method can be called
   * iteratively.
   * 
   * @param document the BioC document
   * @throws XMLStreamException unexpected processing errors
   */
  public void writeDocument(BioCDocument document)
      throws XMLStreamException {
    if (!hasWrittenCollectionInfo) {
      throw new IllegalStateException(
          "writeCollectionInfo should be invoked before.");
    }
    writer.write(document);
  }
}
