package com.pengyifan.bioc.util;

import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.io.BioCCollectionReader;
import org.junit.Ignore;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Deprecated
@Ignore
public class BioCValidate2Test {

  private static final String XML_FILENAME = "xml/PMID-8557975-simplified-sentences.xml";

  @Test
  public void testCheck() throws Exception {
    BioCCollection collection = getCollection();
    BioCValidate2.check(collection);
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