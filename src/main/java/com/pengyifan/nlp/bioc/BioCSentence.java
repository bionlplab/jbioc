package com.pengyifan.nlp.bioc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.Maps;

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

  private int offset;
  private String text;
  private Map<String, String> infons;
  private Map<String, BioCAnnotation> annotations;
  private Map<String, BioCRelation> relations;

  public BioCSentence() {
    offset = -1;
    text = null;
    infons = Maps.newHashMap();
    annotations = Maps.newHashMap();
    relations = Maps.newHashMap();
  }

  /**
   * Constructs a builder initialized with the current sentence. Use this to
   * derive a new sentence from the current one.
   */
  public BioCSentence(BioCSentence sentence) {
    this();
    setOffset(sentence.offset);
    setInfons(sentence.infons);
    setText(sentence.text);
    annotations.putAll(sentence.annotations);
    relations.putAll(sentence.relations);
  }

  public void addAnnotation(BioCAnnotation annotation) {
    checkNotNull(annotation, "annotation cannot be null");
    checkArgument(annotations.containsKey(annotation.getID()));
    this.annotations.put(annotation.getID(), annotation);
  }

  public void addRelation(BioCRelation relation) {
    checkNotNull(relation, "relation cannot be null");
    checkArgument(relations.containsKey(relation.getID()));
    this.relations.put(relation.getID(), relation);
  }

  public void clearAnnotations() {
    annotations.clear();
  }

  public void clearInfons() {
    infons.clear();
  }

  public void clearRelations() {
    annotations.clear();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BioCSentence)) {
      return false;
    }
    BioCSentence rhs = (BioCSentence) obj;
    return Objects.equals(offset, rhs.offset)
        && Objects.equals(text, rhs.text)
        && Objects.equals(infons, rhs.infons)
        && Objects.equals(annotations, rhs.annotations)
        && Objects.equals(relations, rhs.relations);
  }

  /**
   * Returns the annotation at the specified position in this sentence.
   */
  public BioCAnnotation getAnnotation(String annotationID) {
    return annotations.get(annotationID);
  }
  
  /**
   * Annotations on the text of the passage.
   */
  public Collection<BioCAnnotation> getAnnotations() {
    return annotations.values();
  }

  /**
   * Returns the value to which the specified key is mapped, or null if this
   * {@code infons} contains no mapping for the key.
   */
  public Optional<String> getInfon(String key) {
    return Optional.ofNullable(infons.get(key));
  }

  /**
   * Returns the information in the sentence.
   */
  public Map<String, String> getInfons() {
    return infons;
  }

  /**
   * A {@link BioCDocument} offset to where the sentence begins in the
   * {@link BioCPassage}. This value is the sum of the passage offset and the
   * local offset within the passage.
   */
  public int getOffset() {
    checkArgument(offset != -1, "offset has to be set");
    return offset;
  }

  /**
   * Returns the relation at the specified position in this sentence.
   */
  public BioCRelation getRelation(String relationID) {
    return relations.get(relationID);
  }

  /**
   * Relations between the annotations and possibly other relations on the text
   * of the sentence.
   */
  public Collection<BioCRelation> getRelations() {
    return relations.values();
  }

  /**
   * The original text of the sentence.
   */
  public Optional<String> getText() {
    return Optional.ofNullable(text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(offset, text, infons, annotations, relations);
  }

  public void putInfon(String key, String value) {
    infons.put(key, value);
  }

  public void removeInfon(String key) {
    infons.remove(key);
  }

  public void setInfons(Map<String, String> infons) {
    clearInfons();
    this.infons.putAll(infons);
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("offset", offset)
        .append("text", text)
        .append("infons", infons)
        .append("annotations", annotations)
        .append("relations", relations)
        .toString();
  }

}
