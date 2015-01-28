package com.pengyifan.nlp.bioc.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.lang3.Validate;

import com.pengyifan.nlp.bioc.BioCCollection;
import com.pengyifan.nlp.bioc.BioCDocument;

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

  /**
   * Returns the charset encoding of the BioC file.
   */
  public String getEncoding() {
    return encoding;
  }

  /**
   * Sets the charset encoding of the BioC file.
   */
  public void setEncoding(String encoding) {
    Validate.notNull(encoding);
    this.encoding = encoding;
  }

  /**
   * Gets the xml version declared on the xml declaration. Returns null if none
   * was declared
   */
  public String getVersion() {
    return version;
  }

  /**
   * Sets the xml version declared on the xml declaration.
   */
  public void setVersion(String version) {
    Validate.notNull(version);
    this.version = version;
  }

  /**
   * Gets the standalone declaration from the xml declaration
   */
  public boolean isStandalone() {
    return standalone;
  }

  /**
   * Sets the standalone declaration to the xml declaration
   */
  public void setStandalone(boolean standalone) {
    this.standalone = standalone;
  }

  /**
   * Sets the absolute URI of the BioC DTD file.
   */
  public void setDTD(String dtd) {
    this.dtd = dtd;
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
   * Writes the BioC document information: source, date, key, infons
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
   * Writes the BioC document into the xml file. This method can be called
   * iteratively.
   */
  public void writeDocument(BioCDocument document)
      throws XMLStreamException {
    if (!hasWrittenCollectionInfo) {
      throw new IllegalStateException(
          "writeCollectionInfo should be invoked before.");
    }
    writer.write(document);
  }

  /**
   * Returns the absolute URI of the BioC DTD file.
   */
  public String getDTD() {
    return dtd;
  }
}
