package bioc.test;

/**
 * Another copy the XML program. This time, also copy the data structure before
 * writing instead of just writing the original data
 **/

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.xml.stream.XMLStreamException;

import bioc.BioCCollection;
import bioc.BioCDocument;
import bioc.io.BioCDocumentReader;
import bioc.io.BioCDocumentWriter;
import bioc.io.BioCFactory;
import bioc.test.CopyConverter;

public class Copy2_XML {

  public static void main(String[] args)
      throws XMLStreamException, IOException {

    if (args.length != 2) {
      System.err.println("usage: java Copy2_XML in.xml out.xml");
      System.exit(-1);
    }

    Copy2_XML copy_xml = new Copy2_XML();
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
    
    BioCCollection collection = reader.readCollectionInfo();

    CopyConverter converter = new CopyConverter();
    BioCCollection outCollection = converter.getCollection(collection);
    writer.writeCollectionInfo(outCollection);

    for ( BioCDocument document : reader ) {
    	BioCDocument outDocument = converter.getDocument(document);
    	writer.writeDocument(outDocument);
    }

    reader.close();
    writer.close();
  }
}
