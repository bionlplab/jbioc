package bioc.test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import bioc.BioCCollection;
import bioc.io.BioCCollectionReader;
import bioc.io.BioCCollectionWriter;
import bioc.io.BioCFactory;

public class Copy5_XML {

  public static void main(String[] args)
      throws IOException, XMLStreamException {

    if (args.length != 2) {
      System.err.println("usage: java -jar Copy_XML in.xml out.xml");
      System.exit(-1);
    }

    Copy5_XML copy_xml = new Copy5_XML();

    // test udel
    copy_xml.copy(args[0], args[1] + ".udel", BioCFactory.STANDARD);

    // test nih
    copy_xml.copy(args[0], args[1] + ".nil", BioCFactory.WOODSTOX);
  }

  public void copy(String inXML, String outXML, String flags)
      throws XMLStreamException, IOException {
    BioCFactory factory = BioCFactory.newFactory(flags);
    BioCCollectionReader reader = factory
        .createBioCCollectionReader(new FileReader(inXML));
    BioCCollectionWriter writer = factory
        .createBioCCollectionWriter(new FileWriter(outXML));

    BioCCollection collection = reader.readCollection();
    writer.writeCollection(collection);

    reader.close();
    writer.close();
  }

}
