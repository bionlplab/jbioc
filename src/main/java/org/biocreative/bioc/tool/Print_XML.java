package org.biocreative.bioc.tool;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.io.woodstox.ConnectorWoodstox;

public class Print_XML {

  public static void main(String[] args)
      throws FileNotFoundException, XMLStreamException {
    Print_XML px = new Print_XML();
    px.parseFile("xml/PMID-8557975-simplified-sentences-tokens.xml");
  }

  public void parseFile(String filename)
      throws FileNotFoundException, XMLStreamException {

    ConnectorWoodstox connector = new ConnectorWoodstox();
    BioCCollection collection = connector.startRead(new FileReader(filename));
    System.out.println(collection);

    while (connector.hasNext()) {
      BioCDocument document = connector.next();
      System.out.println(document);
    }

  }
}
