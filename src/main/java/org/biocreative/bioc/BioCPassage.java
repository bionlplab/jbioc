package org.biocreative.bioc;

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
 * One passage in a {@link BioCDocument}.
 * <p>
 * This might be the {@code text} in the passage and possibly
 * {@link BioCAnnotation}s over that text. It could be the {@link BioCSentence}
 * s in the passage. In either case it might include {@link BioCRelation}s over
 * annotations on the passage.
 */
public class BioCPassage {

  private int offset;
  private String text;
  private Map<String, String> infons;
  private List<BioCSentence> sentences;
  private List<BioCAnnotation> annotations;
  private List<BioCRelation> relations;

  public BioCPassage() {
    offset = -1;
    infons = Maps.newHashMap();
    annotations = Lists.newArrayList();
    relations = Lists.newArrayList();
    sentences = Lists.newArrayList();
  }

  /**
   * Constructs a builder initialized with the current passage. Use this to
   * derive a new passage from the current one.
   */
  public BioCPassage(BioCPassage passage) {
    this();
    setOffset(passage.offset);
    setAnnotations(passage.annotations);
    setInfons(passage.infons);
    setRelations(passage.relations);
    setSentences(passage.sentences);
    setText(passage.text);
  }

  public void addAnnotation(BioCAnnotation annotation) {
    checkNotNull(annotation, "annotation cannot be null");
    this.annotations.add(annotation);
  }

  public void addRelation(BioCRelation relation) {
    checkNotNull(relation, "relation cannot be null");
    this.relations.add(relation);
  }

  public void addSentence(BioCSentence sentence) {
    checkNotNull(sentence, "sentence cannot be null");
    this.sentences.add(sentence);
  }

  /**
   * Returns a unmodifiable iterator over the annotations in this passage in
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

  public void clearSentences() {
    sentences.clear();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BioCPassage)) {
      return false;
    }
    BioCPassage rhs = (BioCPassage) obj;
    return Objects.equals(offset, rhs.offset)
        && Objects.equals(text, rhs.text)
        && Objects.equals(infons, rhs.infons)
        && Objects.equals(sentences, rhs.sentences)
        && Objects.equals(annotations, rhs.annotations)
        && Objects.equals(relations, rhs.relations);
  }

  /**
   * Returns the annotation at the specified position in this passage.
   */
  public BioCAnnotation getAnnotation(int index) {
    return annotations.get(index);
  }

  /**
   * Returns the number of annotations in this passage.
   */
  public int getAnnotationCount() {
    return annotations.size();
  }

  /**
   * Annotations on the text of the passage.
   */
  public List<BioCAnnotation> getAnnotations() {
    return annotations;
  }

  /**
   * Returns the value to which the specified key is mapped, or null if this
   * {@code infons} contains no mapping for the key.
   */
  public String getInfon(String key) {
    return infons.get(key);
  }

  /**
   * Information of text in the passage.
   * <p>
   * For PubMed references, it might be "title" or "abstract". For full text
   * papers, it might be Introduction, Methods, Results, or Conclusions. Or
   * they might be paragraphs.
   */
  public Map<String, String> getInfons() {
    return infons;
  }

  /**
   * The offset of the passage in the parent document. The significance of the
   * exact value may depend on the source corpus. They should be sequential and
   * identify the passage's position in the document. Since Pubmed is extracted
   * from an XML file, the title has an offset of zero, while the abstract is
   * assumed to begin after the title and one space.
   */
  public int getOffset() {
    checkArgument(offset >= 0, "offset has to be >= 0");
    return offset;
  }

  /**
   * Returns the relation at the specified position in this passage.
   */
  public BioCRelation getRelation(int index) {
    return relations.get(index);
  }

  /**
   * Returns the number of relations in this passage.
   */
  public int getRelationCount() {
    return relations.size();
  }

  /**
   * Relations between the annotations and possibly other relations on the text
   * of the passage.
   */
  public List<BioCRelation> getRelations() {
    return relations;
  }

  /**
   * Returns the sentence at the specified position in this passage.
   */
  public BioCSentence getSentence(int index) {
    return sentences.get(index);
  }

  /**
   * Returns the number of sentences in this passage.
   */
  public int getSentenceCount() {
    return sentences.size();
  }

  /**
   * The sentences of the passage.
   */
  public List<BioCSentence> getSentences() {
    return sentences;
  }

  /**
   * The original text of the passage.
   */
  public Optional<String> getText() {
    return Optional.ofNullable(text);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(offset, text, infons, sentences, annotations, relations);
  }

  public void putInfon(String key, String value) {
    infons.put(key, value);
  }

  /**
   * Returns a unmodifiable iterator over the relations in this passage in
   * proper sequence.
   */
  public Iterator<BioCRelation> relationIterator() {
    return relations.iterator();
  }

  public void removeInfon(String key) {
    infons.remove(key);
  }

  /**
   * Returns a unmodifiable iterator over the sentences in this passage in
   * proper sequence.
   */
  public Iterator<BioCSentence> sentenceIterator() {
    return sentences.iterator();
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
    this.relations.addAll(relations);
  }

  public void setSentences(List<BioCSentence> sentences) {
    clearSentences();
    this.sentences.addAll(sentences);
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
        .append("sentences", sentences)
        .append("annotations", annotations)
        .append("relations", relations)
        .toString();
  }

}
