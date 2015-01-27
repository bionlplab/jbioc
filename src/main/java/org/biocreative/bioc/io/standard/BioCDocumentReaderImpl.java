package org.biocreative.bioc.io.standard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.biocreative.bioc.io.BioCDocumentReader;
import org.biocreative.bioc.io.standard.BioCReader.Level;

class BioCDocumentReaderImpl implements BioCDocumentReader {
  
  BioCReader reader;

  BioCDocumentReaderImpl(InputStream inputStream)
      throws FactoryConfigurationError, XMLStreamException {
    this(new InputStreamReader(inputStream));
  }

  BioCDocumentReaderImpl(Reader in)
      throws FactoryConfigurationError, XMLStreamException {
   reader = new BioCReader(in, Level.DOCUMENT_LEVEL);
   reader.read();
  }

  BioCDocumentReaderImpl(File inputFile)
      throws FactoryConfigurationError, XMLStreamException,
      FileNotFoundException {
    this(new FileInputStream(inputFile));
  }

  BioCDocumentReaderImpl(String inputFilename)
      throws FactoryConfigurationError, XMLStreamException,
      FileNotFoundException {
    this(new FileInputStream(inputFilename));
  }

  @Override
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

  @Override
  public BioCCollection readCollectionInfo()
      throws XMLStreamException {
    return reader.collectionBuilder.build();
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

  @Override
  public String getDTD() {
    return reader.getDtd();
  }

  @Override
  public void close()
      throws IOException {
    reader.close();
  }

}
