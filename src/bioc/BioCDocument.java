package bioc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Each {@code Document} in the {@link Collection}.
 * 
 * An id, typically from the original corpus, identifies the particular
 * document.
 */
public class BioCDocument {

  /**
   * Id to identify the particular {@code Document}.
   */
  protected String              id;

  protected Map<String, String> infons;

  /**
   * List of passages that comprise the document.
   * 
   * For PubMed references, they might be "title" and "abstract". For full text
   * papers, they might be Introduction, Methods, Results, and Conclusions. Or
   * they might be paragraphs.
   */
  protected List<BioCPassage>   passages;

  public BioCDocument() {
    id = "";
    infons = new HashMap<String, String>();
    passages = new ArrayList<BioCPassage>();
  }

  public BioCDocument(BioCDocument document) {
    id = document.id;
    infons = new HashMap<String, String>(document.infons);
    passages = new ArrayList<BioCPassage>(document.passages);
  }

  /**
   * @return the id
   */
  public String getID() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setID(String id) {
    this.id = id;
  }

  /**
   * @return the infons
   */
  public Map<String, String> getInfons() {
    return infons;
  }

  public String getInfon(String key) {
    return infons.get(key);
  }

  public void putInfon(String key, String value) {
    infons.put(key, value);
  }

  /**
   * @return the passages
   */
  public List<BioCPassage> getPassages() {
    return passages;
  }

  /**
   * @param passages the passages to set
   */
  public void addPassage(BioCPassage passage) {
    passages.add(passage);
  }

  @Override
  public String toString() {
    String s = "id: " + id;
    s += "\n";
    s += "infon: " + infons;
    s += "\n";
    s += passages;
    return s;
  }
}
