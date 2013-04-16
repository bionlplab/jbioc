package bioc.io;

import java.io.Reader;
import java.io.Writer;

public abstract class BioCFactory {

  public static final int NIH  = 1;
  public static final int UDEL = 2;

  public static BioCFactory newFactory(int flags) {
    switch (flags) {
    case UDEL:
      return new udel.bioc.io.BioCFactoryImpl();
    case NIH:
      return new bioc.io.nih.BioCFactoryImpl();
    default:
      return new udel.bioc.io.BioCFactoryImpl();
    }
  }

  public abstract BioCCollectionWriter createBioCCollectionWriter(Writer out);

  public abstract BioCDocumentWriter createBioCDocumentWriter(Writer out);

  public abstract BioCCollectionReader createBioCCollectionReader(Reader in);

  public abstract BioCDocumentReader createBioCDocumentReader(Reader in);

}
