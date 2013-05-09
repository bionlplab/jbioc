package bioc.test;

import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import bioc.BioCCollection;
import bioc.BioCDocument;
import bioc.io.BioCDocumentReader;
import bioc.io.BioCDocumentWriter;
import bioc.io.BioCFactory;

public class Copy_XML {

  public static void main(String[] args)
      throws XMLStreamException, IOException {

    if (args.length != 2) {
      System.err.println("usage: java -jar Copy_XML in.xml out.xml");
      System.exit(-1);
    }

    Copy_XML copy_xml = new Copy_XML();
    copy_xml.copy(args[0], args[1]);
  }

  public void copy(String inXML, String outXML)
      throws XMLStreamException, IOException {

	  BioCFactory factory = BioCFactory.newFactory(BioCFactory.WOODSTOX);
	  BioCDocumentReader reader =
			  factory.createBioCDocumentReader(new FileReader(inXML));
	  BioCDocumentWriter writer =
			  factory.createBioCDocumentWriter(
					  new OutputStreamWriter(
							  new FileOutputStream(outXML), "UTF-8"));
//	  factory.createBioCDocumentWriter(new FileWriter(outXML));

    BioCCollection collection = reader.readCollectionInfo();
    writer.writeCollectionInfo(collection);

    for (BioCDocument document : reader) {
    	writer.writeDocument(document);
    }
    reader.close();
    writer.close();
  }
}
