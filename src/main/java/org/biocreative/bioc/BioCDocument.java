package org.biocreative.bioc;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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

  private BioCDocument() {
  }

  /**
   * Returns the id to identify the particular {@code Document}.
   */
  public String getID() {
    return id;
  }

  /**
   * Returns the information in the document.
   */
  public Map<String, String> getInfons() {
    return infons;
  }

  /**
   * Returns the value to which the specified key is mapped, or null if this
   * {@code infons} contains no mapping for the key.
   */
  public Optional<String> getInfon(String key) {
    return Optional.ofNullable(infons.get(key));
  }

  /**
   * Returns annotations of the passage.
   */
  public List<BioCAnnotation> getAnnotations() {
    return annotations;
  }

  /**
   * Returns the annotation at the specified position in this document.
   */
  public BioCAnnotation getAnnotation(int index) {
    return annotations.get(index);
  }

  /**
   * Returns a unmodifiable iterator over the annotation in this document in
   * proper sequence.
   */
  public Iterator<BioCAnnotation> annotationIterator() {
    return annotations.iterator();
  }

  /**
   * Returns relations between the annotations and possibly other relations on
   * the text of the document.
   */
  public List<BioCRelation> getRelations() {
    return relations;
  }

  /**
   * Returns the relation at the specified position in this document.
   */
  public BioCRelation getRelation(int index) {
    return relations.get(index);
  }

  /**
   * Returns a unmodifiable iterator over the relations in this document in
   * proper sequence.
   */
  public Iterator<BioCRelation> relationIterator() {
    return relations.iterator();
  }

  /**
   * Returns the number of relations in this document.
   */
  public int getRelationCount() {
    return relations.size();
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
   * Returns the number of passages in this document.
   */
  public int getPassageCount() {
    return passages.size();
  }

  /**
   * Returns the passage at the specified position in this document.
   */
  public BioCPassage getPassage(int index) {
    return passages.get(index);
  }

  /**
   * Returns an unmodifiable iterator over the passages in this document in
   * proper sequence.
   */
  public Iterator<BioCPassage> passageIterator() {
    return passages.iterator();
  }

  /**
   * Returns the number of annotations in this document.
   */
  public int getAnnotationCount() {
    return annotations.size();
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, infons, passages, relations);
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
   * Constructs a new builder. Use this to derive a new document.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Constructs a builder initialized with the current document. Use this to
   * derive a new document from the current one.
   */
  public Builder toBuilder() {
    return newBuilder()
        .setID(id)
        .setAnnotations(annotations)
        .setInfons(infons)
        .setRelations(relations)
        .setPassages(passages);
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

  public static class Builder {

    private String id;
    private Map<String, String> infons;
    private List<BioCAnnotation> annotations;
    private List<BioCRelation> relations;
    private List<BioCPassage> passages;

    private Builder() {
      infons = Maps.newHashMap();
      annotations = Lists.newArrayList();
      relations = Lists.newArrayList();
      passages = Lists.newArrayList();
    }

    public Builder setID(String id) {
      Validate.notNull(id, "id cannot be null");
      this.id = id;
      return this;
    }

    public Builder setInfons(Map<String, String> infons) {
      this.infons = Maps.newHashMap(infons);
      return this;
    }

    public Builder clearInfons() {
      infons.clear();
      return this;
    }

    public Builder putInfon(String key, String value) {
      infons.put(key, value);
      return this;
    }

    public Builder removeInfon(String key) {
      infons.remove(key);
      return this;
    }

    public Builder setAnnotations(List<BioCAnnotation> annotations) {
      this.annotations = Lists.newArrayList(annotations);
      return this;
    }

    public Builder addAnnotation(BioCAnnotation annotation) {
      Validate.notNull(annotation, "annotation cannot be null");
      this.annotations.add(annotation);
      return this;
    }

    public Builder clearAnnotations() {
      annotations.clear();
      return this;
    }

    public Builder setRelations(List<BioCRelation> relations) {
      this.relations = Lists.newArrayList(relations);
      return this;
    }

    public Builder clearRelations() {
      annotations.clear();
      return this;
    }

    public Builder addRelation(BioCRelation relation) {
      Validate.notNull(relation, "relation cannot be null");
      this.relations.add(relation);
      return this;
    }

    public Builder setPassages(List<BioCPassage> passages) {
      this.passages = Lists.newArrayList(passages);
      return this;
    }

    public Builder addPassage(BioCPassage passage) {
      Validate.notNull(passage, "passage cannot be null");
      this.passages.add(passage);
      return this;
    }

    public Builder clearPassages() {
      passages.clear();
      return this;
    }

    public Builder clearID() {
      id = null;
      return this;
    }

    public BioCDocument build() {
      checkArguments();

      BioCDocument result = new BioCDocument();
      result.id = id;
      result.infons = ImmutableMap.copyOf(infons);
      result.annotations = ImmutableList.copyOf(annotations);
      result.relations = ImmutableList.copyOf(relations);
      result.passages = ImmutableList.copyOf(passages);

      return result;
    }

    private void checkArguments() {
      Validate.isTrue(id != null, "id has to be set");
    }
  }
}
