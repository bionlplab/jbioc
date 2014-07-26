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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;

/**
 * Each {@code BioCDocument} in the {@link BioCCollection}.
 * 
 * An id, typically from the original corpus, identifies the particular
 * document. It includes {@link BioCPassage}s in the document and possibly
 * {@link BioCRelation}s over annotations on the document.
 */
public class BioCDocument {

  /**
   * Id to identify the particular {@code Document}.
   */
  private String id;

  private ImmutableMap<String, String> infons;

  /**
   * List of passages that comprise the document.
   * 
   * For PubMed references, they might be "title" and "abstract". For full text
   * papers, they might be Introduction, Methods, Results, and Conclusions. Or
   * they might be paragraphs.
   */
  private ImmutableList<BioCPassage> passages;

  /**
   * Annotations on the text of the passage.
   */
  private ImmutableList<BioCAnnotation> annotations;
  
  /**
   * Relations between the annotations and possibly other relations on the text
   * of the document.
   */
  private ImmutableList<BioCRelation> relations;

  private BioCDocument() {
  }

  public String getID() {
    return id;
  }

  public ImmutableMap<String, String> getInfons() {
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

  public UnmodifiableIterator<BioCRelation> relationIterator() {
    return relations.iterator();
  }
  
  public int getRelationCount() {
    return relations.size();
  }

  public ImmutableList<BioCPassage> getPassages() {
    return passages;
  }
  
  public int getPassageCount() {
    return passages.size();
  }

  public BioCPassage getPassage(int index) {
    return passages.get(index);
  }

  public UnmodifiableIterator<BioCPassage> passageIterator() {
    return passages.iterator();
  }
  
  public int getAnnotationCount() {
    return annotations.size();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(id)
        .append(infons)
        .append(passages)
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
    BioCDocument rhs = (BioCDocument) obj;
    return new EqualsBuilder()
        .append(id, rhs.id)
        .append(infons, rhs.infons)
        .append(passages, rhs.passages)
        .append(relations, rhs.relations)
        .isEquals();
  }

  public static Builder newBuilder() {
    return new Builder();
  }
  
  public Builder getBuilder() {
    Builder builder = newBuilder()
        .setID(id)
        .setAnnotations(annotations)
        .setInfons(infons)
        .setRelations(relations)
        .setPassages(passages);
    return builder;
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
      infons = new Hashtable<String, String>();
      annotations = new ArrayList<BioCAnnotation>();
      relations = new ArrayList<BioCRelation>();
      passages = new ArrayList<BioCPassage>();
    }
    
    public Builder setID(String id) {
      Validate.notNull(id, "id cannot be null");
      this.id = id;
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
    
    public Builder setPassages(List<BioCPassage> passages) {
      this.passages = new ArrayList<BioCPassage>(passages);
      return this;
    }

    public Builder addPassage(BioCPassage passage) {
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
