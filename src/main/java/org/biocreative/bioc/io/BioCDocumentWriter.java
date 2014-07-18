package org.biocreative.bioc.io;

import java.io.Closeable;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;

public interface BioCDocumentWriter extends Closeable {

  public void writeBeginCollectionInfo(BioCCollection collection)
      throws XMLStreamException;

  public void writeDocument(BioCDocument document)
      throws XMLStreamException;

  public void setDTD(String dtd);
  
  public String getDTD();
  
  public String getEncoding();

  public void setEncoding(String encoding);

  public String getVersion();

  public void setVersion(String version);

  public boolean isStandalone();

  public void setStandalone(boolean standalone);
}
