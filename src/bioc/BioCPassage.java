package bioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.Relation;

/**
 * One passage in a {@link Document}.
 * 
 * This might be the {@code text} in the passage and possibly
 * {@link Annotation}s over that text. It could be the
 * {@link Sentence}s in the passage. In either case it might include
 * {@link Relation}s over annotations on the passage.
 */
public class BioCPassage {

  /**
   * The offset of the passage in the parent document. The significance of the
   * exact value may depend on the source corpus. They should be sequential and
   * identify the passage's position in the document. Since pubmed is extracted
   * from an XML file, the title has an offset of zero, while the abstract is
   * assumed to begin after the title and one space.
   */
  protected int                  offset;

  /**
   * The original text of the passage.
   */
  protected String               text;

  /**
   * Information of text in the passage.
   * 
   * For PubMed references, it might be "title" or "abstract". For full text
   * papers, it might be Introduction, Methods, Results, or Conclusions. Or
   * they might be paragraphs.
   */
  protected Map<String, String>  infons;

  /**
   * The sentences of the passage.
   */
  protected List<BioCSentence>   sentences;

  /**
   * Annotations on the text of the passage.
   */
  protected List<BioCAnnotation> annotations;

  /**
   * Relations between the annotations and possibly other relations on the text
   * of the passage.
   */
  protected List<BioCRelation>   relations;

  public BioCPassage() {
    offset = -1;
    text = "";
    infons = new HashMap<String, String>();
    sentences = new ArrayList<BioCSentence>();
    annotations = new ArrayList<BioCAnnotation>();
    relations = new ArrayList<BioCRelation>();
  }

  public BioCPassage(BioCPassage passage) {
    offset = passage.offset;
    text = passage.text;
    infons = new HashMap<String, String>(passage.infons);

    sentences = new ArrayList<BioCSentence>();
    for (BioCSentence sen : passage.sentences) {
      sentences.add(new BioCSentence(sen));
    }

    annotations = new ArrayList<BioCAnnotation>();
    for (BioCAnnotation ann : passage.annotations) {
      annotations.add(new BioCAnnotation(ann));
    }

    relations = new ArrayList<BioCRelation>();
    for (BioCRelation rel : passage.relations) {
      relations.add(new BioCRelation(rel));
    }
  }

  public void addAnnotation(BioCAnnotation annotation) {
    annotations.add(annotation);
  }

  public void addRelation(BioCRelation relation) {
    relations.add(relation);
  }

  /**
   * @param sentences the sentences to set
   */
  public void addSentence(BioCSentence sentence) {
    sentences.add(sentence);
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

  /**
   * @return the offset
   */
  public int getOffset() {
    return offset;
  }

  /**
   * @return the relations
   */
  public List<BioCRelation> getRelations() {
    return relations;
  }

  /**
   * @return the sentences
   */
  public List<BioCSentence> getSentences() {
    return sentences;
  }

  /**
   * @return the text
   */
  public String getText() {
    return text;
  }

  public void putInfon(String key, String value) {
    infons.put(key, value);
  }

  /**
   * @param offset the offset to set
   */
  public void setOffset(int offset) {
    this.offset = offset;
  }

  /**
   * @param text the text to set
   */
  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    String s = "infons: " + infons;
    s += "\n";
    s += "offset: " + offset;
    s += "\n";
    s += text;
    s += "\n";
    s += sentences;
    s += "\n";
    s += annotations;
    s += "\n";
    s += relations;
    s += "\n";
    return s;
  }
}
