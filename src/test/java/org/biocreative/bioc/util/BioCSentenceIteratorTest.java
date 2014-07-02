package org.biocreative.bioc.util;

import java.io.FileReader;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.util.BioCSentenceIterator;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCSentence;
import org.biocreative.bioc.io.BioCCollectionReader;
import org.biocreative.bioc.io.BioCFactory;

/**
 * Test BioCCollectionReader and BioCCollectionWriter
 */
public class BioCSentenceIteratorTest {

  public static void main(String[] args)
      throws IOException, XMLStreamException {
    BioCSentenceIteratorTest test = new BioCSentenceIteratorTest();
    test.test("xml/PMID-8557975-simplified-sentences.xml", BioCFactory.STANDARD);
  }

  public void test(String inXML, String flags)
      throws XMLStreamException, IOException {
    BioCFactory factory = BioCFactory.newFactory(flags);

    BioCCollectionReader reader = factory
        .createBioCCollectionReader(new FileReader(inXML));
    BioCCollection collection = reader.readCollection();
    reader.close();

    BioCSentenceIterator itr = new BioCSentenceIterator(collection);
    while (itr.hasNext()) {
      BioCSentence sentence = itr.next();
      System.out.println(sentence.getText());
    }
  }

}
