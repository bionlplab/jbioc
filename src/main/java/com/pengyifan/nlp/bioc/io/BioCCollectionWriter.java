package com.pengyifan.nlp.bioc.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.lang3.Validate;

import com.pengyifan.nlp.bioc.BioCCollection;
import com.pengyifan.nlp.bioc.BioCDocument;

/**
 * The class specifies how to write the BioC file. The users need to set all
 * attributes before, then calls writeCollection once to write the whole BioC
 * collection. Method close is required at the end to finish the writing task
 * and free any resources associated with the writer.
 */
public class BioCCollectionWriter implements Closeable {

  private BioCWriter writer;
  private String dtd;
  private String encoding;
  private String version;
  private boolean standalone;
  private boolean hasWritten;

  public BioCCollectionWriter(OutputStream out)
      throws FactoryConfigurationError, XMLStreamException {
    this(new OutputStreamWriter(out));
  }

  public BioCCollectionWriter(Writer out)
      throws FactoryConfigurationError, XMLStreamException {
    writer = new BioCWriter(out);
    hasWritten = false;
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

  @Override
  public final void close()
      throws IOException {
    writer.close();
  }

  /**
   * Sets the absolute URI of the BioC DTD file.
   */
  public void setDTD(String dtd) {
    this.dtd = dtd;
  }

  /**
   * Writes the whole BioC collection into the xml file. This method can only
   * be called once.
   */
  public void writeCollection(BioCCollection collection)
      throws XMLStreamException {
    if (hasWritten) {
      throw new IllegalStateException(
          "writeCollection can only be invoked once.");
    }
    hasWritten = true;

    Validate.notNull(dtd, "haven't set DTD yet");

    writer
        .writeStartDocument(encoding, version, standalone)
        .writeDTD(dtd)
        .writeBeginCollectionInfo(collection);

    for (BioCDocument doc : collection.getDocuments()) {
      writer.write(doc);
    }

    // end collection
    writer.writeEndCollection()
        .writeEndDocument();
  }

  /**
   * Returns the absolute URI of the BioC DTD file.
   */
  public String getDTD() {
    return dtd;
  }
}
