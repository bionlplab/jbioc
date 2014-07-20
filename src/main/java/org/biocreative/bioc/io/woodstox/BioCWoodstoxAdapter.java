package org.biocreative.bioc.io.woodstox;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.io.BioCCollectionReader;
import org.biocreative.bioc.io.BioCCollectionWriter;
import org.biocreative.bioc.io.BioCDocumentReader;
import org.biocreative.bioc.io.BioCDocumentWriter;

class BioCWoodstoxAdapter implements BioCCollectionReader, BioCDocumentWriter,
    BioCCollectionWriter, BioCDocumentReader {

  ConnectorWoodstox inConnector;
  ConnectorWoodstox outConnector;
  Reader in;
  Writer out;

  private String encoding;
  private String version;
  private boolean standalone;

  private BioCCollection collection;

  BioCWoodstoxAdapter(Reader in) {
    inConnector = new ConnectorWoodstox();
    this.in = in;
  }

  BioCWoodstoxAdapter(Writer out) {
    outConnector = new ConnectorWoodstox();
    this.out = out;
  }

  @Override
  public void close()
      throws IOException {
    if (outConnector != null) {
      try {
        outConnector.endWrite();
      } catch (XMLStreamException e) {
        throw new IOException(e.getMessage(), e);
      }
    }
  }

  @Override
  public BioCDocument readDocument()
      throws XMLStreamException {
    if (inConnector.hasNext()) {
      return inConnector.next();
    } else {
      return null;
    }
  }

  @Override
  public BioCCollection readCollectionInfo()
      throws XMLStreamException {
    if (collection == null) {
      collection = inConnector.startRead(in);
    }
    return collection;
  }

  @Override
  public void writeCollection(BioCCollection collection)
      throws XMLStreamException {
    writeBeginCollectionInfo(collection);
    for (BioCDocument doc : collection.getDocuments()) {
      writeDocument(doc);
    }
  }

  @Override
  public void writeBeginCollectionInfo(BioCCollection collection)
      throws XMLStreamException {
    outConnector.startWrite(out, collection);
  }

  @Override
  public void writeDocument(BioCDocument document)
      throws XMLStreamException {
    outConnector.writeNext(document);
  }

  @Override
  public void setDTD(String dtd) {
    outConnector.dtd = dtd;
  }

  @Override
  public BioCCollection readCollection()
      throws XMLStreamException {
    if (collection != null) {
      return null;
    }
    collection = readCollectionInfo();

    BioCCollection.Builder collectionBuilder = collection.getBuilder();
    BioCDocument doc = null;
    while ((doc = readDocument()) != null) {
      collectionBuilder.addDocument(doc);
    }
    return collectionBuilder.build();
  }

  @Override
  public Iterator<BioCDocument> iterator() {
    return inConnector;
  }

  @Override
  public String getEncoding() {
    return encoding;
  }

  @Override
  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  @Override
  public String getVersion() {
    return version;
  }

  @Override
  public void setVersion(String version) {
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
  public String getDTD() {
    return inConnector.dtd;
  }

}
