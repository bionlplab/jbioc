package org.biocreative.bioc.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.io.BioCReader.Level;

/**
 * The class allows forward, read-only access to BioC file. It is designed to
 * read the entire BioC file into one collection.
 */
public class BioCCollectionReader implements Closeable {

  private BioCCollection collection;
  private BioCReader reader;

  public BioCCollectionReader(InputStream inputStream)
      throws FactoryConfigurationError, XMLStreamException {
    this(new InputStreamReader(inputStream));
  }

  public BioCCollectionReader(Reader in)
      throws FactoryConfigurationError, XMLStreamException {
    reader = new BioCReader(in, Level.COLLECTION_LEVEL);
    reader.read();
    collection = reader.collection;
  }

  public BioCCollectionReader(File inputFile)
      throws FactoryConfigurationError, XMLStreamException,
      FileNotFoundException {
    this(new FileReader(inputFile));
  }

  public BioCCollectionReader(String inputFilename)
      throws FactoryConfigurationError, XMLStreamException,
      FileNotFoundException {
    this(new FileReader(inputFilename));
  }

  /**
   * Returns the collection of documents.
   */
  public BioCCollection readCollection()
      throws XMLStreamException {
    if (collection != null) {
      BioCCollection thisCollection = collection;
      collection = null;
      return thisCollection;
    }
    return null;
  }

  /**
   * Returns the absolute URI of the BioC DTD file.
   */
  public String getDTD() {
    return reader.getDtd();
  }

  @Override
  public void close()
      throws IOException {
    reader.close();
  }
}
