package bioc.io.standard;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import bioc.BioCCollection;
import bioc.BioCDocument;
import bioc.io.BioCDocumentWriter;

class BioCDocumentWriterImpl extends BioCAllWriter implements
    BioCDocumentWriter {

  String dtd;

  public BioCDocumentWriterImpl(OutputStream outputStream)
      throws FactoryConfigurationError, XMLStreamException {
    super(outputStream);
    dtd = "BioC.dtd";
  }

  public BioCDocumentWriterImpl(Writer out)
      throws FactoryConfigurationError, XMLStreamException {
    super(out);
    dtd = "BioC.dtd";
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
      writer.writeEndElement();

      writer.writeEndDocument();
      writer.flush();
      writer.close();
    } catch (XMLStreamException e) {
      e.printStackTrace();
      throw new IOException(e.getMessage());
    }
  }

  @Override
  public void writeCollectionInfo(BioCCollection collection)
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
  }

  @Override
  public void writeDocument(BioCDocument document)
      throws XMLStreamException {
    write(document);
  }
}
