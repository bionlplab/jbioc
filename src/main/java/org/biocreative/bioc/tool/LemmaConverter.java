package org.biocreative.bioc.tool;

import java.util.HashMap;
import java.util.Map;

import org.biocreative.bioc.BioCAnnotation;
import org.biocreative.bioc.util.CopyConverter;

import edu.ucdenver.ccp.nlp.biolemmatizer.BioLemmatizer;

/**
 * org.biocreative.bioc wrapper for the BioLemmatizer (http://biolemmatizer.sourceforge.net)
 * reads the org.biocreative.bioc input xml file, generates lemmas for the corresponding 
 * token and its POS tag, inserts the resulting lemmas into the 
 * corresponding annotation of the xml file following the org.biocreative.bioc-compliant
 * format, and finally outputs the updated org.biocreative.bioc xml file.
 */
public class LemmaConverter extends CopyConverter{
  /** lemma lookup cache */
  private Map<String, String> lemmaLookupMap;
  
  /** load BioLemmatizer */
  private static BioLemmatizer bioLemmatizer = new BioLemmatizer();
  
  /**
   * constructor to initialize class fields of LemmaConverter
   */
  public LemmaConverter () {
      lemmaLookupMap = new HashMap<String, String>();
  }
  
  /**
   * read from the org.biocreative.bioc annotation each token and its POS tag,
   * generate corresponding lemma, and insert the lemma
   * into the corresponding annotation following org.biocreative.bioc format
   */
  @Override
  public BioCAnnotation getAnnotation(BioCAnnotation in) {
      BioCAnnotation out = new BioCAnnotation();
      out.setID(in.getID());
      out.setInfons(in.getInfons());
      out.setText(in.getText());
      out.setLocations(in.getLocations());
      String pos = out.getInfon("POS");
      String token = out.getText();
      String lemma = generateLemma(token, pos); 
      out.putInfon("lemma", lemma);
      return out;
  }
 
  /**
   * Generate lemma for the input String with POS tag
   * using the BioLemmatizer, and populate the lemma
   * lookup map
   */
  private String generateLemma(String token, String pos) {
      String lemma;
      if(lemmaLookupMap.containsKey(token + " " + pos)) {
         return lemmaLookupMap.get(token + " " + pos);
      }

      lemma = bioLemmatizer.lemmatizeByLexiconAndRules(token, pos).lemmasToString();
      lemmaLookupMap.put((token + " " + pos), lemma );

      return lemma;
  }

}