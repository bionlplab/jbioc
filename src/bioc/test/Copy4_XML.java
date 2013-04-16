package bioc.test;

/**
 * Another copy the XML program. This time, also copy the data structure before
 * writing instead of just writing the original data
 **/

import java.io.FileReader;
import java.io.FileWriter;

import bioc.BioCCollection;
import bioc.io.woodstox.ConnectorWoodstox;

// import bioc_util.CopyConverter;

public class Copy4_XML {

  public static void main(String[] args)
      throws Exception {

    if (args.length != 2) {
      System.err.println("usage: java Copy4_XML in.xml out.xml");
      System.exit(-1);
    }

    Copy4_XML copy_xml = new Copy4_XML();
    copy_xml.copy(args[0], args[1]);
  }

  public void copy(String inXML, String outXML)
      throws Exception {
    /*
     * FileReader test = new FileReader(inXML); BufferedReader br = new
     * BufferedReader(test); String s = br.readLine(); System.out.println(s); s
     * = br.readLine(); System.out.println(s); return;
     */

    ConnectorWoodstox inConnector = new ConnectorWoodstox();

    // OK, uses a FileReader, but that uses the same code as processing a
    // String
    FileReader in = new FileReader(inXML);

    BioCCollection collection = inConnector.parseXMLCollection(in);

    ConnectorWoodstox outConnector = new ConnectorWoodstox();
    BioCCollection outCollection = new BioCCollection(collection);

    // CopyConverter converter = new CopyConverter();
    // BioCCollection outCollection = converter.getCollection(collection);

    String xml = outConnector.toXML(outCollection);
    FileWriter out = new FileWriter(outXML);
    out.write(xml);
    out.close();

  }
}
