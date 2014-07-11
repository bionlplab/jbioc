package org.biocreative.bioc.io;

import java.io.Reader;
import java.io.Writer;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.lang3.Validate;
import org.biocreative.bioc.io.standard.JdkStrategy;
import org.biocreative.bioc.io.woodstox.WoodstoxStrategy;

public class BioCFactory {

  public static final String WOODSTOX = "WOODSTOX";
  public static final String STANDARD = "STANDARD";

  private BioCXMLStrategy strategy;

  private BioCFactory(BioCXMLStrategy stragegy) {
    this.strategy = stragegy;
  }
  
  public BioCXMLStrategy getStrategy() {
    return strategy;
  }

  /**
   * @deprecated use {@link newFactory(BioCXMLStrategy strategy)}} instead
   * @param stragegy
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
      return newFactory(new WoodstoxStrategy());
    }
  }

  public static BioCFactory newFactory(BioCXMLStrategy strategy) {
    return new BioCFactory(strategy);
  }

  public BioCCollectionWriter createBioCCollectionWriter(Writer out)
      throws XMLStreamException {
    return strategy.createBioCCollectionWriter(out);
  }

  public BioCDocumentWriter createBioCDocumentWriter(Writer out)
      throws XMLStreamException {
    return strategy.createBioCDocumentWriter(out);
  }

  public BioCCollectionReader createBioCCollectionReader(Reader in)
      throws XMLStreamException {
    return strategy.createBioCCollectionReader(in);
  }

  public BioCDocumentReader createBioCDocumentReader(Reader in)
      throws XMLStreamException {
    return strategy.createBioCDocumentReader(in);
  }

}
