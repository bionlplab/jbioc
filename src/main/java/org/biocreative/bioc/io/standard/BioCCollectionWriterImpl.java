package org.biocreative.bioc.io.standard;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.lang3.Validate;
import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.io.BioCCollectionWriter;

class BioCCollectionWriterImpl implements
    BioCCollectionWriter {

  BioCWriter writer;
  String dtd;
  String encoding;
  String version;
  boolean standalone;
  boolean hasWritten;

  BioCCollectionWriterImpl(OutputStream out)
      throws FactoryConfigurationError, XMLStreamException {
    this(new OutputStreamWriter(out));
  }

  BioCCollectionWriterImpl(Writer out)
      throws FactoryConfigurationError, XMLStreamException {
    writer = new BioCWriter(out);
    hasWritten = false;
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
  public final void close()
      throws IOException {
    writer.close();
  }

  @Override
  public void setDTD(String dtd) {
    this.dtd = dtd;
  }

  @Override
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

  @Override
  public String getDTD() {
    return dtd;
  }
}
