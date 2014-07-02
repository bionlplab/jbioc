package org.biocreative.bioc.util;

import java.io.FileReader;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCPassage;
import org.biocreative.bioc.io.BioCCollectionReader;
import org.biocreative.bioc.io.BioCFactory;
import org.biocreative.bioc.util.BioCPassageIterator;

/**
 * Test BioCCollectionReader and BioCCollectionWriter
 */
public class BioCPassageIteratorTest {

  public static void main(String[] args)
      throws IOException, XMLStreamException {
    BioCPassageIteratorTest test = new BioCPassageIteratorTest();
    test.test("xml/PMID-8557975-simplified-sentences.xml", BioCFactory.STANDARD);
  }

  public void test(String inXML, String flags)
      throws XMLStreamException, IOException {
    BioCFactory factory = BioCFactory.newFactory(flags);

    BioCCollectionReader reader = factory
        .createBioCCollectionReader(new FileReader(inXML));
    BioCCollection collection = reader.readCollection();
    reader.close();

    BioCPassageIterator itr = new BioCPassageIterator(collection);
    while (itr.hasNext()) {
      BioCPassage passage = itr.next();
      System.out.println(passage.getText());
    }
  }

}
