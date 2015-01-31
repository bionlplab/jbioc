package com.pengyifan.bioc.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;

/**
 * Writes the entire collection into BioC file. The users need to set all
 * collection information before, then calls
 * {@link #writeCollection(BioCCollection)} once to write the whole BioC
 * collection. Method close is required at the end to finish the writing task
 * and free any resources associated with the writer. For example,
 * <p>
 * 
 * <pre>
 * BioCCollectionWriter writer = new BioCCollectionWriter("foo.xml");
 * writer.writeCollection(collection);
 * writer.close();
 * </pre>
 * 
 * @since 1.0.0
 * @see BioCDocumentWriter
 * @author Yifan Peng
 */
public class BioCCollectionWriter implements Closeable {

  private BioCWriter writer;
  private boolean hasWritten;

  /**
   * Creates a new BioCCollectionReader, given the File object.
   * 
   * @param file a File object to write to
   * @throws FactoryConfigurationError if a factory configuration error occurs
   * @throws XMLStreamException if an unexpected processing error occurs
   * @throws IOException if the file exists but is a directory rather than a
   *           regular file, does not exist but cannot be created, or cannot be
   *           opened for any other reason
   */
  public BioCCollectionWriter(File file)
      throws FactoryConfigurationError, XMLStreamException,
      IOException {
    this(new FileWriter(file));
  }

  /**
   * Creates a BioCCollectionWriter that uses the output stream out.
   * 
   * @param out an OutputStream
   * @throws FactoryConfigurationError if a factory configuration error occurs
   * @throws XMLStreamException if an unexpected processing error occurs
   */
  public BioCCollectionWriter(OutputStream out)
      throws FactoryConfigurationError, XMLStreamException {
    this(new OutputStreamWriter(out));
  }

  /**
   * Creates a BioCCollectionWriter that uses the writer out.
   * 
   * @param out a Writer
   * @throws FactoryConfigurationError if a factory configuration error occurs
   * @throws XMLStreamException if an unexpected processing error occurs
   */
  public BioCCollectionWriter(Writer out)
      throws FactoryConfigurationError, XMLStreamException {
    writer = new BioCWriter(out);
    hasWritten = false;
  }

  /**
   * Creates a new BioCCollectionWriter, given the name of the file to read
   * from.
   * 
   * @param fileName the name of the file to read from
   * @throws FactoryConfigurationError if a factory configuration error occurs
   * @throws XMLStreamException if an unexpected processing error occurs
   * @throws IOException if the named file exists but is a directory rather
   *           than a regular file, does not exist but cannot be created, or
   *           cannot be opened for any other reason
   */
  public BioCCollectionWriter(String fileName)
      throws FactoryConfigurationError, XMLStreamException,
      IOException {
    this(new FileWriter(fileName));
  }

  /**
   * Closes the writer, flushing it first. Once the writer has been closed,
   * further writeCollection() invocations will cause an IOException to be
   * thrown. Closing a previously closed writer has no effect.
   */
  @Override
  public final void close()
      throws IOException {
    writer.close();
  }

  /**
   * Writes the whole BioC collection into the BioC file. This method can only
   * be called once.
   * 
   * @param collection the BioC collection
   * @throws XMLStreamException if an unexpected processing error occurs
   */
  public void writeCollection(BioCCollection collection)
      throws XMLStreamException {
    if (hasWritten) {
      throw new IllegalStateException(
          "writeCollection can only be invoked once.");
    }
    hasWritten = true;

    writer.writeStartDocument(
        collection.getEncoding(),
        collection.getVersion(),
        collection.isStandalone())
        .writeDTD(collection.getDtd())
        .writeBeginCollectionInfo(collection);

    for (BioCDocument doc : collection.getDocuments()) {
      writer.write(doc);
    }

    // end collection
    writer.writeEndCollection()
        .writeEndDocument();
  }
}
