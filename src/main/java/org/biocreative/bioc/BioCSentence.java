package org.biocreative.bioc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;

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
  private ImmutableMap<String, String> infons;
  private ImmutableList<BioCAnnotation> annotations;
  private ImmutableList<BioCRelation> relations;

  private BioCSentence() {
  }

  /**
   * A {@link BioCDocument} offset to where the sentence begins in the
   * {@link BioCPassage}. This value is the sum of the passage offset and the
   * local offset within the passage.
   */
  public int getOffset() {
    return offset;
  }

  /**
   * The original text of the sentence.
   */
  public Optional<String> getText() {
    return Optional.ofNullable(text);
  }

  /**
   * Returns the information in the sentence.
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
   * {@link BioCAnnotation}s on the original text
   */
  public ImmutableList<BioCAnnotation> getAnnotations() {
    return annotations;
  }

  /**
   * Returns the annotation at the specified position in this sentence.
   */
  public BioCAnnotation getAnnotation(int index) {
    return annotations.get(index);
  }

  /**
   * Returns a unmodifiable iterator over the annotations in this sentence in
   * proper sequence.
   */
  public UnmodifiableIterator<BioCAnnotation> annotationIterator() {
    return annotations.iterator();
  }

  /**
   * Relations between the annotations and possibly other relations on the text
   * of the sentence.
   */
  public ImmutableList<BioCRelation> getRelations() {
    return relations;
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
   * Returns the number of annotations in this sentence.
   */
  public int getAnnotationCount() {
    return annotations.size();
  }

  /**
   * Returns a unmodifiable iterator over the relations in this sentence in
   * proper sequence.
   */
  public UnmodifiableIterator<BioCRelation> relationIterator() {
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

  @Override
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
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("offset", offset)
        .append("text", text)
        .append("infons", infons)
        .append("annotations", annotations)
        .append("relations", relations)
        .toString();
  }

  /**
   * Constructs a new builder. Use this to derive a new sentence.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Constructs a builder initialized with the current sentence. Use this to
   * derive a new sentence from the current one.
   */
  public Builder toBuilder() {
    Builder builder = newBuilder()
        .setOffset(offset)
        .setAnnotations(annotations)
        .setInfons(infons)
        .setRelations(relations);
    if (getText().isPresent()) {
      builder.setText(text);
    }
    return builder;
  }

  public static class Builder {

    private int offset;
    private String text;
    private Map<String, String> infons;
    private List<BioCAnnotation> annotations;
    private List<BioCRelation> relations;

    private Builder() {
      offset = -1;
      infons = new Hashtable<String, String>();
      annotations = new ArrayList<BioCAnnotation>();
      relations = new ArrayList<BioCRelation>();
    }

    public Builder setOffset(int offset) {
      Validate.isTrue(offset >= 0, "offset has to be >= 0");
      this.offset = offset;
      return this;
    }

    public Builder setInfons(Map<String, String> infons) {
      this.infons = new Hashtable<String, String>(infons);
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

    public Builder setText(String text) {
      Validate.notNull(text, "text cannot be null");
      this.text = text;
      return this;
    }

    public Builder setAnnotations(List<BioCAnnotation> annotations) {
      this.annotations = new ArrayList<BioCAnnotation>(annotations);
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
      this.relations = new ArrayList<BioCRelation>(relations);
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

    public Builder clearText() {
      text = null;
      return this;
    }

    public BioCSentence build() {
      checkArguments();

      BioCSentence result = new BioCSentence();
      result.offset = offset;
      result.text = text;
      result.infons = ImmutableMap.copyOf(infons);
      result.annotations = ImmutableList.copyOf(annotations);
      result.relations = ImmutableList.copyOf(relations);

      return result;
    }

    private void checkArguments() {
      Validate.isTrue(offset != -1, "offset has to be set");
    }
  }
}
