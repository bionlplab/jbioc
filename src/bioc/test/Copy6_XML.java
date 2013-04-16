package bioc.test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import bioc.BioCCollection;
import bioc.BioCDocument;
import bioc.io.BioCDocumentReader;
import bioc.io.BioCDocumentWriter;
import bioc.io.BioCFactory;

public class Copy6_XML {

  public static void main(String[] args)
      throws IOException, XMLStreamException {

    if (args.length != 2) {
      System.err.println("usage: java -jar Copy_XML in.xml out.xml");
      System.exit(-1);
    }

    Copy6_XML copy_xml = new Copy6_XML();

    // test udel
    copy_xml.copy(args[0], args[1] + ".udel", BioCFactory.STANDARD);

    // test nih
    copy_xml.copy(args[0], args[1] + ".nil", BioCFactory.WOODSTOX);
  }

  public void copy(String inXML, String outXML, String flags)
      throws XMLStreamException, IOException {
    BioCFactory factory = BioCFactory.newFactory(flags);
    BioCDocumentReader reader = factory
        .createBioCDocumentReader(new FileReader(inXML));
    BioCDocumentWriter writer = factory
        .createBioCDocumentWriter(new FileWriter(outXML));

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
