package com.pengyifan.bioc;

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
 * One document in the {@link BioCCollection}.
 * 
 * An id, typically from the original corpus, identifies the particular
 * document. It includes {@link BioCPassage}s in the document and possibly
 * {@link BioCRelation}s over annotations on the document.
 */
public class BioCDocument {

  private String id;
  private Map<String, String> infons;
  private List<BioCPassage> passages;
  private List<BioCAnnotation> annotations;
  private List<BioCRelation> relations;

  public BioCDocument() {
    id = null;
    infons = Maps.newHashMap();
    annotations = Lists.newArrayList();
    relations = Lists.newArrayList();
    passages = Lists.newArrayList();
  }

  public BioCDocument(BioCDocument document) {
    this();
    setID(document.id);
    setAnnotations(document.annotations);
    setInfons(document.infons);
    setRelations(document.relations);
    setPassages(document.passages);
  }
  
  public void addAnnotation(BioCAnnotation annotation) {
    checkNotNull(annotation, "annotation cannot be null");
    this.annotations.add(annotation);
  }

  public void addPassage(BioCPassage passage) {
    checkNotNull(passage, "passage cannot be null");
    this.passages.add(passage);
  }

  public void addRelation(BioCRelation relation) {
    checkNotNull(relation, "relation cannot be null");
    this.relations.add(relation);
  }

  /**
   * Returns a unmodifiable iterator over the annotation in this document in
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

  public void clearPassages() {
    passages.clear();
  }

  public void clearRelations() {
    annotations.clear();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BioCDocument)) {
      return false;
    }
    BioCDocument rhs = (BioCDocument) obj;
    return Objects.equals(id, rhs.id)
        && Objects.equals(infons, rhs.infons)
        && Objects.equals(passages, rhs.passages)
        && Objects.equals(relations, rhs.relations);
  }

  /**
   * Returns the annotation at the specified position in this document.
   */
  public BioCAnnotation getAnnotation(int index) {
    return annotations.get(index);
  }

  /**
   * Returns the number of annotations in this document.
   */
  public int getAnnotationCount() {
    return annotations.size();
  }

  /**
   * Returns annotations of the passage.
   */
  public List<BioCAnnotation> getAnnotations() {
    return annotations;
  }

  /**
   * Returns the id to identify the particular {@code Document}.
   */
  public String getID() {
    checkNotNull(id, "id has to be set");
    return id;
  }

  /**
   * Returns the value to which the specified key is mapped, or null if this
   * {@code infons} contains no mapping for the key.
   */
  public Optional<String> getInfon(String key) {
    return Optional.ofNullable(infons.get(key));
  }

  /**
   * Returns the information in the document.
   */
  public Map<String, String> getInfons() {
    return infons;
  }

  /**
   * Returns the passage at the specified position in this document.
   */
  public BioCPassage getPassage(int index) {
    return passages.get(index);
  }

  /**
   * Returns the number of passages in this document.
   */
  public int getPassageCount() {
    return passages.size();
  }

  /**
   * Returns the list of passages that comprise the document.
   * <p>
   * For PubMed references, they might be "title" and "abstract". For full text
   * papers, they might be Introduction, Methods, Results, and Conclusions. Or
   * they might be paragraphs.
   */
  public List<BioCPassage> getPassages() {
    return passages;
  }

  /**
   * Returns the relation at the specified position in this document.
   */
  public BioCRelation getRelation(int index) {
    return relations.get(index);
  }

  /**
   * Returns the number of relations in this document.
   */
  public int getRelationCount() {
    return relations.size();
  }

  /**
   * Returns relations between the annotations and possibly other relations on
   * the text of the document.
   */
  public List<BioCRelation> getRelations() {
    return relations;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, infons, passages, relations);
  }

  /**
   * Returns an unmodifiable iterator over the passages in this document in
   * proper sequence.
   */
  public Iterator<BioCPassage> passageIterator() {
    return passages.iterator();
  }

  public void putInfon(String key, String value) {
    infons.put(key, value);
  }

  /**
   * Returns a unmodifiable iterator over the relations in this document in
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

  public void setID(String id) {
    this.id = id;
  }

  public void setInfons(Map<String, String> infons) {
    clearInfons();
    this.infons.putAll(infons);
  }

  public void setPassages(List<BioCPassage> passages) {
    clearPassages();
    this.passages.addAll(passages);
  }

  public void setRelations(List<BioCRelation> relations) {
    clearRelations();
    this.relations.addAll(relations);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("infons", infons)
        .append("passages", passages)
        .append("relations", relations)
        .append("annotations", annotations)
        .toString();
  }
}
