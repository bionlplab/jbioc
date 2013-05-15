package bioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Each {@code BioCDocument} in the {@link BioCCollection}.
 * 
 * An id, typically from the original corpus, identifies the particular
 * document. It includes {@link BioCPassage}s in the document and
 *  possibly {@link BioCRelation}s over annotations on the document.
 */
public class BioCDocument implements Iterable<BioCPassage> {

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

  /**
   * Relations between the annotations and possibly other relations on the text
   * of the document.
   */
  protected List<BioCRelation>   relations;

  public BioCDocument() {
    id = "";
    infons = new HashMap<String, String>();
    passages = new ArrayList<BioCPassage>();
    relations = new ArrayList<BioCRelation>();
 }

  public BioCDocument(BioCDocument document) {
    id = document.id;
    infons = new HashMap<String, String>(document.infons);
    passages = new ArrayList<BioCPassage>(document.passages);
    relations = new ArrayList<BioCRelation>();
    for (BioCRelation rel : document.relations) {
      relations.add(new BioCRelation(rel));
    }
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

  /**
   * @param infons the infons to set
   */
  public void setInfons(Map<String,String> infons) {
    this.infons = infons;
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
   * @return the relations
   */
  public List<BioCRelation> getRelations() {
    return relations;
  }

  /**
   * @param passages the passages to set
   */
  public void addPassage(BioCPassage passage) {
    passages.add(passage);
  }

  public void addRelation(BioCRelation relation) {
    relations.add(relation);
  }

  @Override
  public String toString() {
    String s = "id: " + id;
    s += "\n";
    s += "infon: " + infons;
    s += "\n";
    s += passages;
    s += "\n";
    s += relations;
    s += "\n"; 
    return s;
  }

  @Override
  public Iterator<BioCPassage> iterator() {
    return passages.iterator();
  }
}
