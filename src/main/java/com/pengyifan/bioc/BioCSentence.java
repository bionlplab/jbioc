package com.pengyifan.bioc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.*;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.Maps;

/**
 * One sentence in a {@link BioCPassage}.
 * <p>
 * It may contain the original text of the sentence or it might be
 * {@link BioCAnnotation}s and possibly {@link BioCRelation}s on the text of
 * the passage.
 * <p>
 * There is no code to keep those possibilities mutually exclusive. However the
 * currently available DTDs only describe the listed possibilities.
 * 
 * @since 1.0.0
 * @author Yifan Peng
 */
public class BioCSentence {

  private int offset;
  private String text;
  private Map<String, String> infons;
  private List<BioCAnnotation> annotations;
  private List<BioCRelation> relations;

  /**
   * Constructs an empty sentence.
   */
  public BioCSentence() {
    offset = -1;
    text = null;
    infons = Maps.newHashMap();
    annotations = Lists.newArrayList();
    relations = Lists.newArrayList();
  }

  /**
   * Constructs a sentence containing the information of the specified
   * sentence.
   * 
   * @param sentence the sentence whose information is to be placed into this
   *          passage
   */
  public BioCSentence(BioCSentence sentence) {
    this();
    setOffset(sentence.offset);
    setInfons(sentence.infons);
    setText(sentence.text);
    annotations.addAll(sentence.annotations);
    relations.addAll(sentence.relations);
  }

  /**
   * Adds annotation in this sentence.
   * 
   * @param annotation annotation
   */
  public void addAnnotation(BioCAnnotation annotation) {
    checkNotNull(annotation, "annotation cannot be null");
    checkArgument(
        !getAnnotation(annotation.getID()).isPresent(),
        "duplicated annotation: %s",
        annotation);
    this.annotations.add(annotation);
  }

  /**
   * Adds relation in this sentence.
   * 
   * @param relation relation
   */
  public void addRelation(BioCRelation relation) {
    checkNotNull(relation, "relation cannot be null");
    checkArgument(
        !getRelation(relation.getID()).isPresent(),
        "duplicated relation: %s",
        relation);
    this.relations.add(relation);
  }

  /**
   * Clears all annotations.
   */
  public void clearAnnotations() {
    annotations.clear();
  }

  /**
   * Clears all information.
   */
  public void clearInfons() {
    infons.clear();
  }

  /**
   * Clears all relations.
   */
  public void clearRelations() {
    relations.clear();
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
   * Returns the annotation of the specified ID in this sentence.
   * 
   * @param annotationID id of a specified annotation
   * @return the annotation of the specified ID in this sentence
   */
  public Optional<BioCAnnotation> getAnnotation(String annotationID) {
    return annotations.stream()
        .filter(a -> a.getID().equals(annotationID))
        .findFirst();
  }

  /**
   * Annotations on the text of the sentence.
   * 
   * @return annotations on the text of the sentence
   */
  public Collection<BioCAnnotation> getAnnotations() {
    return annotations;
  }

  /**
   * Returns the value to which the specified key is mapped, or null if this
   * {@code infons} contains no mapping for the key.
   * 
   * @param key the key whose associated value is to be returned
   * @return the value to which the specified key is mapped
   */
  public Optional<String> getInfon(String key) {
    return Optional.ofNullable(infons.get(key));
  }

  /**
   * Returns the information in the sentence.
   * 
   * @return the information in the sentence.
   */
  public Map<String, String> getInfons() {
    return infons;
  }

  /**
   * A {@link BioCDocument} offset to where the sentence begins in the
   * {@link BioCPassage}. This value is the sum of the passage offset and the
   * local offset within the passage.
   * 
   * @return offset to where the sentence begins
   */
  public int getOffset() {
    checkArgument(offset != -1, "offset has to be set");
    return offset;
  }

  /**
   * Returns the relation at the specified position in this sentence.
   * 
   * @param relationID id of a specified relation
   * @return the relation of the specified ID in this sentence
   */
  public Optional<BioCRelation> getRelation(String relationID) {
    return relations.stream()
        .filter(r -> r.getID().equals(relationID))
        .findAny();
  }

  /**
   * Relations between the annotations and possibly other relations on the text
   * of the sentence.
   * 
   * @return relations of the sentence
   */
  public Collection<BioCRelation> getRelations() {
    return relations;
  }

  /**
   * Returns the original text of the sentence.
   * 
   * @return the original text of the sentence
   */
  public Optional<String> getText() {
    return Optional.ofNullable(text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(offset, text, infons, annotations, relations);
  }

  /**
   * Associates the specified value with the specified key in this sentence.
   * 
   * @param key key with which the specified value is to be associated
   * @param value value to be associated with the specified key
   */
  public void putInfon(String key, String value) {
    infons.put(key, value);
  }

  /**
   * Removes the value for a key from this sentence if it is present (optional
   * operation).
   * 
   * @param key key with which the specified value is to be associated
   */
  public void removeInfon(String key) {
    infons.remove(key);
  }

  /**
   * Sets the information in this sentence.
   * 
   * @param infons the information in this sentence
   */
  public void setInfons(Map<String, String> infons) {
    clearInfons();
    this.infons.putAll(infons);
  }

  /**
   * Sets offset to where the sentence begins.
   * 
   * @param offset to where the sentence begins
   */
  public void setOffset(int offset) {
    this.offset = offset;
  }

  /**
   * Sets the original text of the sentence.
   * 
   * @param text the original text
   */
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
