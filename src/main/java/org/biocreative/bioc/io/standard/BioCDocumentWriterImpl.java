package org.biocreative.bioc.io.standard;

import java.io.IOException;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.lang3.Validate;
import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.io.BioCDocumentWriter;

class BioCDocumentWriterImpl implements BioCDocumentWriter {

  BioCWriter writer;
  String dtd;
  String encoding;
  String version;
  boolean standalone;
  boolean hasWrittenCollectionInfo;

  BioCDocumentWriterImpl(Writer out)
      throws FactoryConfigurationError, XMLStreamException {
    writer = new BioCWriter(out);
    hasWrittenCollectionInfo = false;
    encoding = "UTF-8";
    version = "1.0";
    standalone = true;
  }

  @Override
  public String getEncoding() {
    return encoding;
  }

  @Override
  public void setEncoding(String encoding) {
    Validate.notNull(encoding);
    this.encoding = encoding;
  }

  @Override
  public String getVersion() {
    return version;
  }

  @Override
  public void setVersion(String version) {
    Validate.notNull(version);
    this.version = version;
  }

  @Override
  public boolean isStandalone() {
    return standalone;
  }

  @Override
  public void setStandalone(boolean standalone) {
    this.standalone = standalone;
  }

  @Override
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

  @Override
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

  @Override
  public void writeDocument(BioCDocument document)
      throws XMLStreamException {
    if (!hasWrittenCollectionInfo) {
      throw new IllegalStateException(
          "writeCollectionInfo should be invoked before.");
    }
    writer.write(document);
  }

  @Override
  public String getDTD() {
    return dtd;
  }
}
