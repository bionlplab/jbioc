package org.biocreative.bioc.io.standard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.io.BioCCollectionReader;

class BioCCollectionReaderImpl extends BioCReader implements
    BioCCollectionReader {

  BioCCollectionReaderImpl(InputStream inputStream)
      throws FactoryConfigurationError, XMLStreamException {
    super(inputStream);
  }

  BioCCollectionReaderImpl(Reader in)
      throws FactoryConfigurationError, XMLStreamException {
    super(in);
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
    sentenceBuilder = null;
    passageBuilder = null;
    document = null;
    collection = null;
    read();
    return collection;
  }

}
