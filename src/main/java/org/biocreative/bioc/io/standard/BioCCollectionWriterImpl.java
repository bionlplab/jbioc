package org.biocreative.bioc.io.standard;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.io.BioCCollectionWriter;

class BioCCollectionWriterImpl extends BioCAllWriter implements
    BioCCollectionWriter {

  String  dtd;
  boolean hasWritten;

  BioCCollectionWriterImpl(OutputStream out)
      throws FactoryConfigurationError, XMLStreamException {
    this(new OutputStreamWriter(out));
  }

  BioCCollectionWriterImpl(Writer out)
      throws FactoryConfigurationError, XMLStreamException {
    super(out);
    dtd = "org.biocreative.bioc.dtd";
    hasWritten = false;
  }

  @Override
  public final void close()
      throws IOException {
    try {
      writer.writeEndDocument();
      writer.flush();
      writer.close();
    } catch (XMLStreamException e) {
      throw new IOException(e.getMessage());
    }
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

    writer.writeDTD("<!DOCTYPE collection SYSTEM \"org.biocreative.bioc.dtd\">");
    writer.writeStartElement("collection");
    // source
    writer.writeStartElement("source");
    writer.writeCharacters(collection.getSource());
    writer.writeEndElement();
    // date
    writer.writeStartElement("date");
    writer.writeCharacters(collection.getDate());
    writer.writeEndElement();
    // key
    writer.writeStartElement("key");
    writer.writeCharacters(collection.getKey());
    writer.writeEndElement();
    // infon
    write(collection.getInfons());

    for (BioCDocument doc : collection.getDocuments()) {
      write(doc);
    }

    // end collection
    writer.writeEndElement();
  }
}
