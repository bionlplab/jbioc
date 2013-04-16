package bioc.io.standard;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import bioc.BioCCollection;
import bioc.BioCDocument;
import bioc.io.BioCCollectionWriter;

class BioCCollectionWriterImpl extends BioCAllWriter implements
    BioCCollectionWriter {

  String dtd;

  public BioCCollectionWriterImpl(OutputStream outputStream)
      throws FactoryConfigurationError, XMLStreamException {
    super(outputStream);
    dtd = "BioC.dtd";
  }

  public BioCCollectionWriterImpl(Writer out)
      throws FactoryConfigurationError, XMLStreamException {
    super(out);
    dtd = "BioC.dtd";
  }

  @Override
  public final void close()
      throws IOException {
    try {
      writer.writeEndDocument();
      writer.flush();
      writer.close();
    } catch (XMLStreamException e) {
      e.printStackTrace();
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
    writer.writeDTD("<!DOCTYPE collection SYSTEM \"BioC.dtd\">");
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
