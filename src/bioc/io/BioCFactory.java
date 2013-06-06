package bioc.io;

import java.io.Reader;
import java.io.Writer;

import javax.xml.parsers.FactoryConfigurationError;

public abstract class BioCFactory {

  public static final String WOODSTOX = "WOODSTOX";
  public static final String STANDARD = "STANDARD";

  public static BioCFactory newFactory(String flags) {
    if (flags.equals(STANDARD)) {
      return new bioc.io.standard.BioCFactoryImpl();
    } else if (flags.equals(WOODSTOX)) {
      return new bioc.io.woodstox.BioCFactoryImpl();
    } else {
      throw new FactoryConfigurationError(
          "only " + STANDARD + " and " + WOODSTOX + " are supported.");
    }
  }

  public abstract BioCCollectionWriter createBioCCollectionWriter(Writer out);

  public abstract BioCDocumentWriter createBioCDocumentWriter(Writer out);

  public abstract BioCCollectionReader createBioCCollectionReader(Reader in);

  public abstract BioCDocumentReader createBioCDocumentReader(Reader in);

}
