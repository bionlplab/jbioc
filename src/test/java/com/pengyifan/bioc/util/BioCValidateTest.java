package com.pengyifan.bioc.util;

import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCSentence;
import com.pengyifan.bioc.io.BioCCollectionReader;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static org.junit.Assert.*;

@Deprecated
public class BioCValidateTest {

  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";

  @Test
  public void testCheckSentence() throws Exception {
    BioCCollection collection = getCollection();
    BioCSentenceIterator itr = new BioCSentenceIterator(collection);
    while (itr.hasNext()) {
      BioCValidate.check(itr.next());
    }
  }

  @Test
  public void testPassage() throws Exception {
    BioCCollection collection = getCollection();
    BioCPassageIterator itr = new BioCPassageIterator(collection);
    while (itr.hasNext()) {
      BioCValidate.check(itr.next());
    }
  }

  @Test
  public void testDocument() throws Exception {
    BioCCollection collection = getCollection();
    for(BioCDocument document: collection.getDocuments()) {
      BioCValidate.check(document);
    }
  }

  @Test
  public void testCollection() throws Exception {
    BioCCollection collection = getCollection();
    BioCValidate.check(collection);
  }

  private BioCCollection getCollection() throws XMLStreamException, IOException {
    URL url = Thread.currentThread().getContextClassLoader().getSystemResource(XML_FILENAME);
    File biocFile = new File(url.getFile());

    BioCCollectionReader reader = new BioCCollectionReader(biocFile);
    BioCCollection collection = reader.readCollection();
    reader.close();

    return collection;
  }
}