package bioc.io.standard;

import java.io.Reader;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import bioc.io.BioCCollectionReader;
import bioc.io.BioCCollectionWriter;
import bioc.io.BioCDocumentReader;
import bioc.io.BioCDocumentWriter;
import bioc.io.BioCFactory;

public class BioCFactoryImpl extends BioCFactory {

  @Override
  public BioCCollectionWriter createBioCCollectionWriter(Writer out) {
    try {
      return new BioCCollectionWriterImpl(out);
    } catch (FactoryConfigurationError e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public BioCDocumentWriter createBioCDocumentWriter(Writer out) {
    try {
      return new BioCDocumentWriterImpl(out);
    } catch (FactoryConfigurationError e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public BioCCollectionReader createBioCCollectionReader(Reader in) {
    try {
      return new BioCCollectionReaderImpl(in);
    } catch (FactoryConfigurationError e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public BioCDocumentReader createBioCDocumentReader(Reader in) {
    try {
      return new BioCDocumentReaderImpl(in);
    } catch (FactoryConfigurationError e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
    return null;
  }

}
