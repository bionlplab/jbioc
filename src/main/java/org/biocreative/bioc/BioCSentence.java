package org.biocreative.bioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * One sentence in a {@link BioCPassage}.
 * 
 * It may contain the original text of the sentence or it might be
 * {@link BioCAnnotation}s and possibly {@link BioCRelation}s on the text of
 * the passage.
 * 
 * There is no code to keep those possibilities mutually exclusive. However the
 * currently available DTDs only describe the listed possibilities
 */
public class BioCSentence {

  /**
   * A {@link BioCDocument} offset to where the sentence begins in the
   * {@link BioCPassage}. This value is the sum of the passage offset and the
   * local offset within the passage.
   */
  protected int offset;

  /**
   * The original text of the sentence.
   */
  protected String text;
  protected Map<String, String> infons;

  /**
   * {@link BioCAnnotation}s on the original text
   */
  protected List<BioCAnnotation> annotations;

  /**
   * Relations between the annotations and possibly other relations on the text
   * of the sentence.
   */
  protected List<BioCRelation> relations;

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
    infons = new HashMap<String, String>(sentence.infons);

    annotations = new ArrayList<BioCAnnotation>();
    for (BioCAnnotation ann : sentence.annotations) {
      annotations.add(new BioCAnnotation(ann));
    }

    relations = new ArrayList<BioCRelation>();
    for (BioCRelation rel : sentence.relations) {
      relations.add(new BioCRelation(rel));
    }
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    Validate.isTrue(offset >= 0, "offset must be >= 0");
    this.offset = offset;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
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
  public void setInfons(Map<String, String> infons) {
    this.infons = infons;
  }

  public void clearInfons() {
    infons.clear();
  }

  public String getInfon(String key) {
    return infons.get(key);
  }

  public void putInfon(String key, String value) {
    infons.put(key, value);
  }

  public void removeInfon(String key) {
    infons.remove(key);
  }

  public List<BioCAnnotation> getAnnotations() {
    return annotations;
  }

  public void setAnnotations(List<BioCAnnotation> annotations) {
    this.annotations = annotations;
  }

  public void clearAnnotations() {
    annotations.clear();
  }

  public BioCAnnotation getAnnotation(int index) {
    return annotations.get(index);
  }

  public void addAnnotation(BioCAnnotation annotation) {
    annotations.add(annotation);
  }

  public void removeAnnotation(BioCAnnotation annotation) {
    annotations.remove(annotation);
  }

  public void removeAnnotation(int index) {
    annotations.remove(index);
  }

  /**
   * @return iterator over annotations
   */
  public Iterator<BioCAnnotation> annotationIterator() {
    return annotations.iterator();
  }

  /**
   * @return the relations
   */

  public List<BioCRelation> getRelations() {
    return relations;
  }

  public void setRelations(List<BioCRelation> relations) {
    this.relations = relations;
  }

  public void clearRelations() {
    relations.clear();
  }

  public BioCRelation getRelation(int index) {
    return relations.get(index);
  }

  public void addRelation(BioCRelation relation) {
    relations.add(relation);
  }

  public void removeRelation(BioCRelation relation) {
    relations.remove(relation);
  }

  public void removeRelation(int index) {
    relations.remove(index);
  }

  /**
   * @return iterator over relations
   */
  public Iterator<BioCRelation> relationIterator() {
    return relations.iterator();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(offset)
        .append(text)
        .append(infons)
        .append(annotations)
        .append(relations)
        .toHashCode();
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != getClass()) {
      return false;
    }
    BioCSentence rhs = (BioCSentence) obj;
    return new EqualsBuilder()
        .append(offset, rhs.offset)
        .append(text, rhs.text)
        .append(infons, rhs.infons)
        .append(annotations, rhs.annotations)
        .append(relations, rhs.relations)
        .isEquals();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("offset", offset)
        .append("text", text)
        .append("infons", infons)
        .append("annotations", annotations)
        .append("relations", relations)
        .toString();
  }
}
