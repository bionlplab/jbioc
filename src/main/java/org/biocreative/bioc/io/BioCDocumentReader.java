package org.biocreative.bioc.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.io.BioCReader.Level;

/**
 * The class allows forward, read-only access to BioC file. It is designed to
 * sequentially read the BioC file into document every time the method
 * readDocument is called.
 */
public class BioCDocumentReader implements Closeable, Iterable<BioCDocument> {

  private BioCReader reader;

  public BioCDocumentReader(InputStream inputStream)
      throws FactoryConfigurationError, XMLStreamException {
    this(new InputStreamReader(inputStream));
  }

  public BioCDocumentReader(Reader in)
      throws FactoryConfigurationError, XMLStreamException {
    reader = new BioCReader(in, Level.DOCUMENT_LEVEL);
    reader.read();
  }

  public BioCDocumentReader(File inputFile)
      throws FactoryConfigurationError, XMLStreamException,
      FileNotFoundException {
    this(new FileReader(inputFile));
  }

  public BioCDocumentReader(String inputFilename)
      throws FactoryConfigurationError, XMLStreamException,
      FileNotFoundException {
    this(new FileReader(inputFilename));
  }

  /**
   * Read a BioCDocument from the XML file
   */
  public BioCDocument readDocument()
      throws XMLStreamException {

    if (reader.document != null) {
      BioCDocument thisDocument = reader.document;
      reader.read();
      return thisDocument;
    } else {
      return null;
    }
  }

  /**
   * Read the collection information: source, date, key, infons
   */
  public BioCCollection readCollectionInfo()
      throws XMLStreamException {
    return reader.collection;
  }

  @Override
  public Iterator<BioCDocument> iterator() {
    return new Iterator<BioCDocument>() {

      @Override
      public boolean hasNext() {
        return reader.document != null;
      }

      @Override
      public BioCDocument next() {
        BioCDocument thisDocument = reader.document;
        reader.sentence = null;
        reader.passage = null;
        reader.document = null;
        try {
          reader.read();
        } catch (XMLStreamException e) {
          throw new NoSuchElementException(e.getMessage());
        }
        return thisDocument;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException("remove is not supported");
      }

    };
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
