package com.pengyifan.bioc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
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
 * 
 * @since 1.0.0
 * @author Yifan Peng
 */
public class BioCDocument {

  private String id;
  private Map<String, String> infons;
  private List<BioCPassage> passages;
  private Map<String, BioCAnnotation> annotations;
  private Map<String, BioCRelation> relations;

  /**
   * Constructs an empty document.
   */
  public BioCDocument() {
    id = null;
    infons = Maps.newHashMap();
    passages = Lists.newArrayList();
    annotations = Maps.newHashMap();
    relations = Maps.newHashMap();
  }
  
  /**
   * Constructs an empty document with id.
   * 
   * @param id the id used to identify document
   */
  public BioCDocument(String id) {
    this.id = id;
    infons = Maps.newHashMap();
    passages = Lists.newArrayList();
    annotations = Maps.newHashMap();
    relations = Maps.newHashMap();
  }

  /**
   * Constructs an document containing the information of the specified
   * document.
   * 
   * @param document bioc document
   */
  public BioCDocument(BioCDocument document) {
    this();
    setID(document.id);
    setInfons(document.infons);
    setPassages(document.passages);
    annotations.putAll(document.annotations);
    relations.putAll(document.relations);
  }

  /**
   * Adds annotation in this document.
   * 
   * @param annotation annotation
   */
  public void addAnnotation(BioCAnnotation annotation) {
    checkNotNull(annotation, "annotation cannot be null");
    checkArgument(
        !annotations.containsKey(annotation.getID()),
        "duplicated annotation: %s",
        annotation);
    this.annotations.put(annotation.getID(), annotation);
  }

  /**
   * Adds passage in this document.
   * 
   * @param passage passage
   */
  public void addPassage(BioCPassage passage) {
    checkNotNull(passage, "passage cannot be null");
    this.passages.add(passage);
  }

  /**
   * Adds relation in this document.
   * 
   * @param relation relation
   */
  public void addRelation(BioCRelation relation) {
    checkNotNull(relation, "relation cannot be null");
    checkArgument(
        !relations.containsKey(relation.getID()),
        "duplicated relation: %s",
        relation);
    this.relations.put(relation.getID(), relation);
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
   * Clears all passages.
   */
  public void clearPassages() {
    passages.clear();
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
    if (!(obj instanceof BioCDocument)) {
      return false;
    }
    BioCDocument rhs = (BioCDocument) obj;
    return Objects.equals(id, rhs.id)
        && Objects.equals(infons, rhs.infons)
        && Objects.equals(passages, rhs.passages)
        && Objects.equals(annotations, rhs.annotations)
        && Objects.equals(relations, rhs.relations);
  }

  /**
   * Returns the annotation at the specified position in this document.
   * 
   * @param annotationID id of a specified annotation
   * @return the annotation of the specified ID in this document
   */
  public BioCAnnotation getAnnotation(String annotationID) {
    return annotations.get(annotationID);
  }

  /**
   * Annotations on the text of the document.
   * 
   * @return annotations on the text of the document
   */
  public Collection<BioCAnnotation> getAnnotations() {
    return annotations.values();
  }

  /**
   * Returns the id to identify the particular {@code BioCDocument}.
   * 
   * @return the id to identify the particular {@code BioCDocument}
   */
  public String getID() {
    checkNotNull(id, "id has to be set");
    return id;
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
   * Returns the information in the document.
   * 
   * @return the information in the document
   */
  public Map<String, String> getInfons() {
    return infons;
  }

  /**
   * Returns the passage at the specified position in this document.
   * 
   * @param index passage position in this document
   * @return the passage at the specified position in this document
   */
  public BioCPassage getPassage(int index) {
    return passages.get(index);
  }

  /**
   * Returns the number of passages in this document.
   * 
   * @return the number of passages in this document
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
   * 
   * @return passages of the document
   */
  public List<BioCPassage> getPassages() {
    return passages;
  }

  /**
   * Returns the relation at the specified position in this document.
   * 
   * @param relationID id of a specified relation
   * @return the relation of the specified ID in this document
   */
  public BioCRelation getRelation(String relationID) {
    return relations.get(relationID);
  }

  /**
   * Relations between the annotations and possibly other relations on the text
   * of the document.
   * 
   * @return relations of the document
   */
  public Collection<BioCRelation> getRelations() {
    return relations.values();
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, infons, passages, annotations, relations);
  }

  /**
   * Returns an unmodifiable iterator over the passages in this document in
   * proper sequence.
   * 
   * @return an iterator over the passages in this document in proper sequence
   */
  public Iterator<BioCPassage> passageIterator() {
    return passages.iterator();
  }

  /**
   * Associates the specified value with the specified key in this document.
   * 
   * @param key key with which the specified value is to be associated
   * @param value value to be associated with the specified key
   */
  public void putInfon(String key, String value) {
    infons.put(key, value);
  }

  /**
   * Removes the value for a key from this document if it is present (optional
   * operation).
   * 
   * @param key key with which the specified value is to be associated
   */
  public void removeInfon(String key) {
    infons.remove(key);
  }

  /**
   * Sets the id used to identify this document.
   * 
   * @param id the id used to identify document
   */
  public void setID(String id) {
    this.id = id;
  }

  /**
   * Sets the information in this document.
   * 
   * @param infons the information in this document
   */
  public void setInfons(Map<String, String> infons) {
    clearInfons();
    this.infons.putAll(infons);
  }

  /**
   * Sets the passage in this document.
   * 
   * @param passages the passage in this document
   */
  public void setPassages(List<BioCPassage> passages) {
    clearPassages();
    this.passages.addAll(passages);
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
