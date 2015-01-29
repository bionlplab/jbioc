package com.pengyifan.bioc.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;

/**
 * The class specifies how to write the BioC file. The users need to set all
 * attributes before, then calls writeCollection once to write the whole BioC
 * collection. Method close is required at the end to finish the writing task
 * and free any resources associated with the writer.
 */
public class BioCCollectionWriter implements Closeable {

  private BioCWriter writer;
  private boolean hasWritten;

  public BioCCollectionWriter(OutputStream out)
      throws FactoryConfigurationError, XMLStreamException {
    this(new OutputStreamWriter(out));
  }

  public BioCCollectionWriter(Writer out)
      throws FactoryConfigurationError, XMLStreamException {
    writer = new BioCWriter(out);
    hasWritten = false;
  }

  @Override
  public final void close()
      throws IOException {
    writer.close();
  }

  /**
   * Writes the whole BioC collection into the XML file. This method can only
   * be called once.
   * 
   * @param collection the BioC collection
   * @throws XMLStreamException unexpected processing errors
   */
  public void writeCollection(BioCCollection collection)
      throws XMLStreamException {
    if (hasWritten) {
      throw new IllegalStateException(
          "writeCollection can only be invoked once.");
    }
    hasWritten = true;

    writer
        .writeStartDocument(
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
