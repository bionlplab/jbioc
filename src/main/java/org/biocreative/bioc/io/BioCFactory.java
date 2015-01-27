package org.biocreative.bioc.io;

import java.io.Reader;
import java.io.Writer;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.lang3.Validate;
import org.biocreative.bioc.io.standard.JdkStrategy;

/**
 * An object that creates new BioC readers and writers on demand.
 */
public class BioCFactory {

  /**
   * @deprecated use {@link #newFactory(BioCXMLStrategy)} instead
   */
  public static final String WOODSTOX = "WOODSTOX";
  /**
   * @deprecated use {@link #newFactory(BioCXMLStrategy)} instead
   */
  public static final String STANDARD = "STANDARD";

  private BioCXMLStrategy strategy;

  private BioCFactory(BioCXMLStrategy stragegy) {
    this.strategy = stragegy;
  }

  public BioCXMLStrategy getStrategy() {
    return strategy;
  }

  /**
   * @deprecated use {@link #newFactory(BioCXMLStrategy)} instead
   */
  @Deprecated
  public static BioCFactory newFactory(String flags) {

    Validate.isTrue(
        flags.equals(STANDARD) || flags.equals(WOODSTOX),
        "only %s and %s are supported",
        STANDARD,
        WOODSTOX);

    if (flags.equals(STANDARD)) {
      return newFactory(new JdkStrategy());
    } else {
      return null;
    }
  }

  /**
   * Creates a factory instance using the given strategy.
   */
  public static BioCFactory newFactory(BioCXMLStrategy strategy) {
    return new BioCFactory(strategy);
  }

  /**
   * Creates a BioCCollectionWriter instance using the given writer.
   */
  public BioCCollectionWriter createBioCCollectionWriter(Writer out)
      throws XMLStreamException {
    return strategy.createBioCCollectionWriter(out);
  }

  /**
   * Creates a BioCDocumentWriter instance using the given writer.
   */
  public BioCDocumentWriter createBioCDocumentWriter(Writer out)
      throws XMLStreamException {
    return strategy.createBioCDocumentWriter(out);
  }

  /**
   * Creates a BioCCollectionReader instance using the given reader.
   */
  public BioCCollectionReader createBioCCollectionReader(Reader in)
      throws XMLStreamException {
    return strategy.createBioCCollectionReader(in);
  }

  /**
   * Creates a BioCDocumentReader instance using the given reader.
   */
  public BioCDocumentReader createBioCDocumentReader(Reader in)
      throws XMLStreamException {
    return strategy.createBioCDocumentReader(in);
  }

}
