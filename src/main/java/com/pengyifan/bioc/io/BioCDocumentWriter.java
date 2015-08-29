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
 * Writes documents into the file sequentially. The users need to set all
 * attributes before, then calls
 * {@link #writeBeginCollectionInfo(BioCCollection)} to set the collection
 * level's information such as source, date, and key. Then users can call
 * {@link #writeDocument(BioCDocument)} repeatedly to sequentially write BioC
 * documents into the file. Method close is required at the end to finish the
 * writing task and free any resources associated with the writer. For example,
 * <p>
 * 
 * <pre>
 * BioCCollectionWriter writer = new BioCCollectionWriter(&quot;foo.xml&quot;);
 * writer.writeBeginCollectionInfo(collection);
 * for (BioCDocument document : collection.getDocuments()) {
 *   writer.writeDocument(document);
 * }
 * writer.close();
 * </pre>
 * 
 * @since 1.0.0
 * @see BioCCollectionWriter
 * @author Yifan Peng
 */
public class BioCDocumentWriter implements Closeable {

  private BioCWriter writer;
  private boolean hasWrittenCollectionInfo;

  /**
   * Creates a new BioCDocumentWriter, given the File object.
   * 
   * @param file a File object to write to
   * @throws FactoryConfigurationError if a factory configuration error occurs
   * @throws XMLStreamException if an unexpected processing error occurs
   * @throws IOException if the file exists but is a directory rather than a
   *           regular file, does not exist but cannot be created, or cannot be
   *           opened for any other reason
   */
  public BioCDocumentWriter(File file)
      throws FactoryConfigurationError, XMLStreamException,
      IOException {
    this(new FileWriter(file));
  }

  /**
   * Creates a BioCDocumentWriter that uses the output stream out.
   * 
   * @param out an OutputStream
   * @throws FactoryConfigurationError if a factory configuration error occurs
   * @throws XMLStreamException if an unexpected processing error occurs
   */
  public BioCDocumentWriter(OutputStream out)
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
  public BioCDocumentWriter(Writer out)
      throws FactoryConfigurationError, XMLStreamException {
    writer = new BioCWriter(out);
    hasWrittenCollectionInfo = false;
  }

  /**
   * Creates a new BioCDocumentWriter, given the name of the file to read from.
   * 
   * @param fileName the name of the file to read from
   * @throws FactoryConfigurationError if a factory configuration error occurs
   * @throws XMLStreamException if an unexpected processing error occurs
   * @throws IOException if the named file exists but is a directory rather
   *           than a regular file, does not exist but cannot be created, or
   *           cannot be opened for any other reason
   */
  public BioCDocumentWriter(String fileName)
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
    try {
      // end collection
      writer.writeEndCollection()
          .writeEndDocument()
          .close();
    } catch (XMLStreamException e) {
      throw new IOException(e.getMessage());
    }
  }

  /**
   * Writes the BioC collection information: source, date, key, infons, etc.
   * 
   * @param collection the BioC collection whose information will be written
   * @throws XMLStreamException if an unexpected processing error occurs
   */
  public void writeBeginCollectionInfo(BioCCollection collection)
      throws XMLStreamException {

    if (hasWrittenCollectionInfo) {
      throw new IllegalStateException(
          "writeCollectionInfo can only be invoked once.");
    }

    hasWrittenCollectionInfo = true;

    writer
        .writeStartDocument(
            collection.getEncoding(),
            collection.getVersion(),
            collection.isStandalone())
        .writeBeginCollectionInfo(collection);
  }

  /**
   * Writes the BioC document into the XML file. This method can be called
   * sequentially.
   * 
   * @param document the BioC document
   * @throws XMLStreamException if an unexpected processing error occurs
   */
  public void writeDocument(BioCDocument document)
      throws XMLStreamException {
    if (!hasWrittenCollectionInfo) {
      throw new IllegalStateException(
          "writeCollectionInfo should be invoked before.");
    }
    writer.write(document);
  }
}
