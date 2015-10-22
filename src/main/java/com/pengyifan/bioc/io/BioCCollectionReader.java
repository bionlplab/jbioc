package com.pengyifan.bioc.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.io.BioCReader.Level;

/**
 * Reads the entire BioC file into one collection. For example,
 * <p>
 * <pre>
 * BioCCollectionReader reader = new BioCCollectionReader(&quot;foo.xml&quot;);
 * BioCCollection collection = reader.readCollection();
 * reader.close();
 * </pre>
 * 
 * @since 1.0.0
 * @see BioCDocumentReader
 * @author Yifan Peng
 */
public class BioCCollectionReader implements Closeable {

  private BioCCollection collection;
  private BioCReader reader;

  /**
   * Creates a new BioCCollectionReader, given the File to read from.
   * 
   * @param file the File to read from
   * @throws FactoryConfigurationError if a factory configuration error occurs
   * @throws XMLStreamException if an unexpected processing error occurs
   * @throws FileNotFoundException if the file does not exist, is a directory
   *           rather than a regular file, or for some other reason cannot be
   *           opened for reading.
   */
  public BioCCollectionReader(File file)
      throws FactoryConfigurationError, XMLStreamException,
      FileNotFoundException {
    this(new FileReader(file));
  }

  /**
   * Creates a new BioCCollectionReader, given the Path to read from.
   *
   * @param path the file path to read from
   * @throws FactoryConfigurationError if a factory configuration error occurs
   * @throws XMLStreamException if an unexpected processing error occurs
   * @throws IOException if an I/O exception of some sort has occurred
   */
  public BioCCollectionReader(Path path)
      throws FactoryConfigurationError, XMLStreamException,
      IOException {
    this(Files.newBufferedReader(path));
  }

  /**
   * Creates an BioCCollectionReader that uses the input stream in.
   * 
   * @param in an InputStream
   * @throws FactoryConfigurationError if a factory configuration error occurs
   * @throws XMLStreamException if an unexpected processing error occurs
   */
  public BioCCollectionReader(InputStream in)
      throws FactoryConfigurationError, XMLStreamException {
    this(new InputStreamReader(in));
  }

  /**
   * Creates an BioCCollectionReader that uses the reader in.
   * 
   * @param in a Reader
   * @throws FactoryConfigurationError if a factory configuration error occurs
   * @throws XMLStreamException if an unexpected processing error occurs
   */
  public BioCCollectionReader(Reader in)
      throws FactoryConfigurationError, XMLStreamException {
    reader = new BioCReader(in, Level.COLLECTION_LEVEL);
    reader.read();
    collection = reader.collection;
  }

  /**
   * Creates a new BioCCollectionReader, given the name of the file to read
   * from.
   * 
   * @param fileName the name of the file to read from
   * @throws FactoryConfigurationError if a factory configuration error occurs
   * @throws XMLStreamException if an unexpected processing error occurs
   * @throws FileNotFoundException if the file does not exist, is a directory
   *           rather than a regular file, or for some other reason cannot be
   *           opened for reading.
   */
  public BioCCollectionReader(String fileName)
      throws FactoryConfigurationError, XMLStreamException,
      FileNotFoundException {
    this(new FileReader(fileName));
  }

  /**
   * Closes the reader and releases any system resources associated with it.
   * Once the reader has been closed, further readCollection() invocations will
   * throw an IOException. Closing a previously closed reader has no effect.
   */
  @Override
  public void close()
      throws IOException {
    reader.close();
  }

  /**
   * Reads the collection of documents.
   * 
   * @return the BioC collection
   * @throws XMLStreamException if an unexpected processing error occurs
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
}
