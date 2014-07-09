package org.biocreative.bioc.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCPassage;
import org.biocreative.bioc.io.BioCCollectionReader;
import org.biocreative.bioc.io.BioCFactory;
import org.biocreative.bioc.util.BioCPassageIterator;
import org.junit.Test;

/**
 * Test BioCCollectionReader and BioCCollectionWriter
 */
public class BioCPassageIteratorTest {
  
  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";
  private static final String FLAG = BioCFactory.STANDARD;

  @Test
  public void test_success()
      throws XMLStreamException, IOException {
    BioCCollectionReader reader = BioCFactory.newFactory(FLAG)
        .createBioCCollectionReader(
            new InputStreamReader(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(XML_FILENAME)));
    BioCCollection collection = reader.readCollection();
    reader.close();

    List<BioCPassage> passages = new ArrayList<BioCPassage>();
    BioCPassageIterator itr = new BioCPassageIterator(collection);
    while (itr.hasNext()) {
      BioCPassage passage = itr.next();
      System.out.println(passage);
      passages.add(passage);
    }
    
    assertEquals(passages.size(), 1);
    
    BioCPassage passage = passages.get(0);
    assertEquals(passage.getOffset(), 0);
    assertEquals(passage.getInfon("type"), "abstract");
  }

  @Test
  public void test_empty() {
    BioCCollection collection = new BioCCollection();
    BioCPassageIterator itr = new BioCPassageIterator(collection);
    assertFalse(itr.hasNext());
  }
}
