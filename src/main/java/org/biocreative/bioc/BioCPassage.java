package org.biocreative.bioc;

import java.util.ArrayList;
import java.util.HashMap;
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
 * One passage in a {@link BioCDocument}.
 * 
 * This might be the {@code text} in the passage and possibly
 * {@link BioCAnnotation}s over that text. It could be the {@link BioCSentence}
 * s in the passage. In either case it might include {@link BioCRelation}s over
 * annotations on the passage.
 */
public class BioCPassage {

  /**
   * The offset of the passage in the parent document. The significance of the
   * exact value may depend on the source corpus. They should be sequential and
   * identify the passage's position in the document. Since pubmed is extracted
   * from an XML file, the title has an offset of zero, while the abstract is
   * assumed to begin after the title and one space.
   */
  private int offset;

  /**
   * The original text of the passage.
   */
  private String text;

  /**
   * Information of text in the passage.
   * 
   * For PubMed references, it might be "title" or "abstract". For full text
   * papers, it might be Introduction, Methods, Results, or Conclusions. Or
   * they might be paragraphs.
   */
  private ImmutableMap<String, String> infons;

  /**
   * The sentences of the passage.
   */
  private ImmutableList<BioCSentence> sentences;

  /**
   * Annotations on the text of the passage.
   */
  private ImmutableList<BioCAnnotation> annotations;

  /**
   * Relations between the annotations and possibly other relations on the text
   * of the passage.
   */
  private ImmutableList<BioCRelation> relations;

  private BioCPassage() {
  }

  public int getOffset() {
    return offset;
  }

  public Optional<String> getText() {
    return Optional.fromNullable(text);
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

  public ImmutableList<BioCSentence> getSentences() {
    return sentences;
  }
  
  public int getSentenceCount() {
    return sentences.size();
  }

  public BioCSentence getSentence(int index) {
    return sentences.get(index);
  }

  public UnmodifiableIterator<BioCSentence> sentenceIterator() {
    return sentences.iterator();
  }
  
  public int getAnnotationCount() {
    return annotations.size();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(offset)
        .append(text)
        .append(infons)
        .append(sentences)
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
    BioCPassage rhs = (BioCPassage) obj;
    return new EqualsBuilder()
        .append(offset, rhs.offset)
        .append(text, rhs.text)
        .append(infons, rhs.infons)
        .append(sentences, rhs.sentences)
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
        .append("sentences", sentences)
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
        .setRelations(relations)
        .setSentences(sentences);
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
    private List<BioCSentence> sentences;

    private Builder() {
      offset = -1;
      infons = new HashMap<String, String>();
      annotations = new ArrayList<BioCAnnotation>();
      relations = new ArrayList<BioCRelation>();
      sentences = new ArrayList<BioCSentence>();
    }

    public Builder setOffset(int offset) {
      Validate.isTrue(offset >= 0, "offset has to be >= 0");
      this.offset = offset;
      return this;
    }

    public Builder setInfons(Map<String, String> infons) {
      this.infons = new HashMap<String, String>(infons);
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
    
    public Builder setSentences(List<BioCSentence> sentences) {
      this.sentences = new ArrayList<BioCSentence>(sentences);
      return this;
    }

    public Builder addSentence(BioCSentence sentence) {
      this.sentences.add(sentence);
      return this;
    }

    public Builder clearSentences() {
      sentences.clear();
      return this;
    }

    public Builder clear() {
      return clearOffset()
          .clearText()
          .clearAnnotations()
          .clearInfons()
          .clearRelations()
          .clearSentences();
    }

    public Builder clearOffset() {
      offset = -1;
      return this;
    }

    public Builder clearText() {
      text = null;
      return this;
    }

    public BioCPassage build() {
      checkArguments();

      BioCPassage result = new BioCPassage();
      result.offset = offset;
      result.text = text;
      result.infons = ImmutableMap.copyOf(infons);
      result.annotations = ImmutableList.copyOf(annotations);
      result.relations = ImmutableList.copyOf(relations);
      result.sentences = ImmutableList.copyOf(sentences);

      return result;
    }

    private void checkArguments() {
      Validate.isTrue(offset != -1, "offset has to be set");
    }
  }
}
