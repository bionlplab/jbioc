package com.pengyifan.bioc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.Lists;
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
  private List<BioCAnnotation> annotations;
  private List<BioCRelation> relations;

  public BioCSentence() {
    offset = -1;
    text = null;
    infons = Maps.newHashMap();
    annotations = Lists.newArrayList();
    relations = Lists.newArrayList();
  }

  /**
   * Constructs a builder initialized with the current sentence. Use this to
   * derive a new sentence from the current one.
   */
  public BioCSentence(BioCSentence sentence) {
    this();
    setOffset(sentence.offset);
    setAnnotations(sentence.annotations);
    setInfons(sentence.infons);
    setRelations(sentence.relations);
    setText(sentence.text);
  }

  public void addAnnotation(BioCAnnotation annotation) {
    checkNotNull(annotation, "annotation cannot be null");
    this.annotations.add(annotation);
  }

  public void addRelation(BioCRelation relation) {
    checkNotNull(relation, "relation cannot be null");
    this.relations.add(relation);
  }

  /**
   * Returns a unmodifiable iterator over the annotations in this sentence in
   * proper sequence.
   */
  public Iterator<BioCAnnotation> annotationIterator() {
    return annotations.iterator();
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
  public BioCAnnotation getAnnotation(int index) {
    return annotations.get(index);
  }

  /**
   * Returns the number of annotations in this sentence.
   */
  public int getAnnotationCount() {
    return annotations.size();
  }

  /**
   * {@link BioCAnnotation}s on the original text
   */
  public List<BioCAnnotation> getAnnotations() {
    return annotations;
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
  public BioCRelation getRelation(int index) {
    return relations.get(index);
  }

  /**
   * Returns the number of relations in this sentence.
   */
  public int getRelationCount() {
    return relations.size();
  }

  /**
   * Relations between the annotations and possibly other relations on the text
   * of the sentence.
   */
  public List<BioCRelation> getRelations() {
    return relations;
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

  /**
   * Returns a unmodifiable iterator over the relations in this sentence in
   * proper sequence.
   */
  public Iterator<BioCRelation> relationIterator() {
    return relations.iterator();
  }

  public void removeInfon(String key) {
    infons.remove(key);
  }

  public void setAnnotations(List<BioCAnnotation> annotations) {
    clearAnnotations();
    this.annotations.addAll(annotations);
  }

  public void setInfons(Map<String, String> infons) {
    clearInfons();
    this.infons.putAll(infons);
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public void setRelations(List<BioCRelation> relations) {
    clearRelations();
    this.relations.addAll(relations);
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
