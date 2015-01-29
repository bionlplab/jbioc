package com.pengyifan.bioc.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;

/**
 * The class specifies how to write the BioC file. The users need to set all
 * attributes before, then calls writeBeginCollectionInfo to set the collection
 * level's information such as source, date, and key. Then users can call
 * writeDocument repeatedly to sequentially write BioC documents into the file.
 * Method close is required at the end to finish the writing task and free any
 * resources associated with the writer.
 */
public class BioCDocumentWriter implements Closeable {

  private BioCWriter writer;
  private boolean hasWrittenCollectionInfo;

  public BioCDocumentWriter(Writer out)
      throws FactoryConfigurationError, XMLStreamException {
    writer = new BioCWriter(out);
    hasWrittenCollectionInfo = false;
  }

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
   * @throws XMLStreamException unexpected processing errors
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
        .writeDTD(collection.getDtd())
        .writeBeginCollectionInfo(collection);
  }

  /**
   * Writes the BioC document into the XML file. This method can be called
   * iteratively.
   * 
   * @param document the BioC document
   * @throws XMLStreamException unexpected processing errors
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
