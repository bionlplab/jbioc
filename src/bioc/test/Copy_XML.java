package bioc.test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import bioc.BioCCollection;
import bioc.BioCDocument;
import bioc.io.nih.ConnectorWoodstox;

public class Copy_XML {

  public static void main(String[] args)
      throws IOException {

    if (args.length != 2) {
      System.err.println("usage: java -jar Copy_XML in.xml out.xml");
      System.exit(-1);
    }

    Copy_XML copy_xml = new Copy_XML();
    copy_xml.copy(args[0], args[1]);
  }

  public void copy(String inXML, String outXML)
      throws IOException {

    ConnectorWoodstox inConnector = new ConnectorWoodstox();
    BioCCollection collection = inConnector.startRead(new FileReader(inXML));

    ConnectorWoodstox outConnector = new ConnectorWoodstox();
    outConnector.startWrite(new FileWriter(outXML), collection);

    while (inConnector.hasNext()) {
      BioCDocument document = inConnector.next();
      outConnector.writeNext(document);
    }

    outConnector.endWrite();
  }
}
