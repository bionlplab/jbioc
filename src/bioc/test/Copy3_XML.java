package bioc.test;

/**
 * Another copy the XML program. This time, also copy the data structure before
 * writing instead of just writing the original data
 **/

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import bioc.BioCCollection;
import bioc.BioCDocument;
import bioc.io.BioCDocumentReader;
import bioc.io.BioCDocumentWriter;
import bioc.io.BioCFactory;

// import bioc_util.CopyConverter;

public class Copy3_XML {

  public static void main(String[] args)
      throws FactoryConfigurationError, XMLStreamException, IOException {

    if (args.length != 2) {
      System.err.println("usage: java Copy3_XML in.xml out.xml");
      System.exit(-1);
    }

    Copy3_XML copy_xml = new Copy3_XML();
    copy_xml.copy(args[0], args[1]);
  }

  public void copy(String inXML, String outXML)
      throws FactoryConfigurationError, XMLStreamException, IOException {

    BioCFactory factor = BioCFactory.newFactory(BioCFactory.STANDARD);

    BioCDocumentReader reader = factor.createBioCDocumentReader(new FileReader(
        inXML));
    BioCDocumentWriter writer = factor.createBioCDocumentWriter(new FileWriter(
        outXML));

    BioCCollection collection = reader.readCollectionInfo();
    writer.writeCollectionInfo(collection);

    BioCDocument doc = null;
    while ((doc = reader.readDocument()) != null) {
      writer.writeDocument(doc);
    }

    reader.close();
    writer.close();
  }
}
