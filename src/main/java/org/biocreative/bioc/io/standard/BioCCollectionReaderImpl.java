package org.biocreative.bioc.io.standard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.io.BioCCollectionReader;
import org.biocreative.bioc.io.standard.BioCReader.Level;

class BioCCollectionReaderImpl implements
    BioCCollectionReader {
  
  private BioCCollection collection;
  private BioCReader reader;

  BioCCollectionReaderImpl(InputStream inputStream)
      throws FactoryConfigurationError, XMLStreamException {
    this(new InputStreamReader(inputStream));
  }

  BioCCollectionReaderImpl(Reader in)
      throws FactoryConfigurationError, XMLStreamException {
    reader = new BioCReader(in, Level.COLLECTION_LEVEL);
    reader.read();
    collection = reader.collectionBuilder.build();
  }

  BioCCollectionReaderImpl(File inputFile)
      throws FactoryConfigurationError, XMLStreamException,
      FileNotFoundException {
    this(new FileInputStream(inputFile));
  }

  BioCCollectionReaderImpl(String inputFilename)
      throws FactoryConfigurationError, XMLStreamException,
      FileNotFoundException {
    this(new FileInputStream(inputFilename));
  }

  @Override
  public BioCCollection readCollection()
      throws XMLStreamException {
    if (collection != null) {
      BioCCollection thisCollection = collection;
      collection = null;
      return thisCollection;
    }
    return null;
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
