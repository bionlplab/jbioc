package bioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.Relation;

/**
 * One sentence in a {@link Passage}.
 * 
 * It may contain the original text of the sentence or it might be
 * {@link Annotation}s and possibly {@link Relation}s on the text of the
 * passage.
 * 
 * There is no code to keep those possibilities mutually exclusive. However the
 * currently available DTDs only describe the listed possibilities
 */
public class BioCSentence {

  /**
   * A {@code Document} offset to where the sentence begins in the
   * {@code Passage}. This value is the sum of the passage offset and the local
   * offset within the passage.
   */
  protected int                  offset;

  /**
   * The original text of the sentence.
   */
  protected String               text;
  protected Map<String, String>  infons;

  /**
   * {@link Annotation}s on the original text
   */
  protected List<BioCAnnotation> annotations;

  /**
   * Relations between the annotations and possibly other relations on the text
   * of the sentence.
   */
  protected List<BioCRelation>   relations;

  public BioCSentence() {
    offset = -1;
    text = "";
    infons = new HashMap<String, String>();
    annotations = new ArrayList<BioCAnnotation>();
    relations = new ArrayList<BioCRelation>();
  }

  public BioCSentence(BioCSentence sentence) {
    offset = sentence.offset;
    text = sentence.text;

    annotations = new ArrayList<BioCAnnotation>();
    for (BioCAnnotation ann : sentence.annotations) {
      annotations.add(new BioCAnnotation(ann));
    }

    relations = new ArrayList<BioCRelation>();
    for (BioCRelation rel : sentence.relations) {
      relations.add(new BioCRelation(rel));
    }
  }

  public void addAnnotation(BioCAnnotation annotation) {
    annotations.add(annotation);
  }

  public void addRelation(BioCRelation relation) {
    relations.add(relation);
  }

  public List<BioCAnnotation> getAnnotations() {
    return annotations;
  }

  public String getInfon(String key) {
    return infons.get(key);
  }

  /**
   * @param infons the infons to set
   */
  public void setInfons(Map<String,String> infons) {
    this.infons = infons;
  }

  public Map<String, String> getInfons() {
    return infons;
  }

  public int getOffset() {
    return offset;
  }

  public List<BioCRelation> getRelations() {
    return relations;
  }

  public String getText() {
    return text;
  }

  public void putInfon(String key, String value) {
    infons.put(key, value);
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    String s = "offset: " + offset;
    s += "\n";
    s = "infons: " + infons;
    s += "\n";
    s += "text: " + text;
    s += "\n";
    s += annotations;
    s += "\n";
    s += relations;
    s += "\n";
    return s;
  }
}
