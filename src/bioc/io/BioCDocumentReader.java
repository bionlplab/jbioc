package bioc.io;

import java.io.Closeable;

import javax.xml.stream.XMLStreamException;

import bioc.BioCCollection;
import bioc.BioCDocument;

public interface BioCDocumentReader extends Closeable {

  public BioCDocument readDocument()
      throws XMLStreamException;

  public BioCCollection readCollectionInfo()
      throws XMLStreamException;
}
