package bioc.test;

/**
 * Another copy the XML program. This time, also copy the data structure before
 * writing instead of just writing the original data
 **/

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import bioc.BioCCollection;
import bioc.BioCDocument;
import bioc.io.nih.ConnectorWoodstox;

// import bioc_util.CopyConverter;

public class Copy2_XML {

  public static void main(String[] args)
      throws IOException {

    if (args.length != 2) {
      System.err.println("usage: java Copy2_XML in.xml out.xml");
      System.exit(-1);
    }

    Copy2_XML copy_xml = new Copy2_XML();
    copy_xml.copy(args[0], args[1]);
  }

  public void copy(String inXML, String outXML)
      throws IOException {

    ConnectorWoodstox inConnector = new ConnectorWoodstox();
    BioCCollection collection = inConnector.startRead(new FileReader(inXML));

    ConnectorWoodstox outConnector = new ConnectorWoodstox();
    BioCCollection outCollection = new BioCCollection(collection);

    // CopyConverter converter = new CopyConverter();
    // BioCCollection outCollection = converter.getCollection(collection);
    outConnector.startWrite(new FileWriter(outXML), outCollection);

    while (inConnector.hasNext()) {
      BioCDocument document = inConnector.next();
      BioCDocument outDocument = new BioCDocument(document);
      // BioCDocument outDocument = converter.getDocument(document);
      outConnector.writeNext(outDocument);
    }

    outConnector.endWrite();
  }
}
