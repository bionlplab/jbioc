package bioc.test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import edu.ucdenver.ccp.nlp.biolemmatizer.BioLemmatizer;

import bioc.BioCCollection;
import bioc.BioCDocument;
import bioc.io.BioCDocumentReader;
import bioc.io.BioCDocumentWriter;
import bioc.io.BioCFactory;

/**
 * Test BioCDocumentReader and BioCDocumentWriter
 */
public class ModifyXML {
  
  private LemmaConverter converter; 
  
  public ModifyXML() {
    converter = new LemmaConverter();   
  }
  
  public static void main(String[] args)
      throws IOException, XMLStreamException {

    if (args.length != 2) {
      System.err.println("usage: java -jar ModifyXML in.xml out.xml");
      System.exit(-1);
    }
    
    ModifyXML copy_xml = new ModifyXML();

    copy_xml.copy(args[0], args[1], BioCFactory.WOODSTOX);
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
      BioCDocument outDoc = converter.getDocument(doc);
      writer.writeDocument(outDoc);
    }
    reader.close();
    writer.close();

  }
  
    
}
