package org.biocreative.bioc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Optional;
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

  /**
   * A {@link BioCDocument} offset to where the sentence begins in the
   * {@link BioCPassage}. This value is the sum of the passage offset and the
   * local offset within the passage.
   */
  private int offset;

  /**
   * The original text of the sentence.
   */
  private String text;
  private ImmutableMap<String, String> infons;

  /**
   * {@link BioCAnnotation}s on the original text
   */
  private ImmutableList<BioCAnnotation> annotations;

  /**
   * Relations between the annotations and possibly other relations on the text
   * of the sentence.
   */
  private ImmutableList<BioCRelation> relations;

  private BioCSentence() {
  }

  public int getOffset() {
    return offset;
  }

  public Optional<String> getText() {
    return Optional.fromNullable(text);
  }

  public Map<String, String> getInfons() {
    return infons;
  }

  public String getInfon(String key) {
    return infons.get(key);
  }

  public ImmutableList<BioCAnnotation> getAnnotations() {
    return annotations;
  }

  public BioCAnnotation getAnnotation(int index) {
    return annotations.get(index);
  }

  public UnmodifiableIterator<BioCAnnotation> annotationIterator() {
    return annotations.iterator();
  }

  public ImmutableList<BioCRelation> getRelations() {
    return relations;
  }

  public BioCRelation getRelation(int index) {
    return relations.get(index);
  }
  
  public int getRelationCount() {
    return relations.size();
  }
  
  public int getAnnotationCount() {
    return annotations.size();
  }

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

  public static Builder newBuilder() {
    return new Builder();
  }
  
  public Builder getBuilder() {
    Builder builder = newBuilder()
        .setOffset(offset)
        .setAnnotations(annotations)
        .setInfons(infons)
        .setRelations(relations);
    if (getText() != null) {
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
      this.relations.add(relation);
      return this;
    }

    public Builder clearOffset() {
      offset = -1;
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
