package org.biocreative.bioc.io;

import java.io.Closeable;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;

public interface BioCCollectionWriter extends Closeable {

  public void writeCollection(BioCCollection collection)
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
