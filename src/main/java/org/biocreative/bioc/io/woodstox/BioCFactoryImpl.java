package org.biocreative.bioc.io.woodstox;

import java.io.Reader;
import java.io.Writer;

import org.biocreative.bioc.io.BioCCollectionReader;
import org.biocreative.bioc.io.BioCCollectionWriter;
import org.biocreative.bioc.io.BioCDocumentReader;
import org.biocreative.bioc.io.BioCDocumentWriter;
import org.biocreative.bioc.io.BioCFactory;

public class BioCFactoryImpl extends BioCFactory {

  @Override
  public BioCCollectionWriter createBioCCollectionWriter(Writer out) {
    return new BioCWoodstoxAdapter(out);
  }

  @Override
  public BioCDocumentWriter createBioCDocumentWriter(Writer out) {
    return new BioCWoodstoxAdapter(out);
  }

  @Override
  public BioCCollectionReader createBioCCollectionReader(Reader in) {
    return new BioCWoodstoxAdapter(in);
  }

  @Override
  public BioCDocumentReader createBioCDocumentReader(Reader in) {
    return new BioCWoodstoxAdapter(in);
  }

}
