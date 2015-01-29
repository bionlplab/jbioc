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
  private Map<String, BioCAnnotation> annotations;
  private Map<String, BioCRelation> relations;

  public BioCPassage() {
    offset = -1;
    text = null;
    infons = Maps.newHashMap();
    annotations = Maps.newHashMap();
    relations = Maps.newHashMap();
    sentences = Lists.newArrayList();
  }

  /**
   * Constructs a builder initialized with the current passage. Use this to
   * derive a new passage from the current one.
   * 
   * @param passage bioc passage
   */
  public BioCPassage(BioCPassage passage) {
    this();
    setOffset(passage.offset);
    setInfons(passage.infons);
    setSentences(passage.sentences);
    setText(passage.text);
    annotations.putAll(passage.annotations);
    relations.putAll(passage.relations);
  }

  /**
   * Adds annotation in this passage.
   * 
   * @param annotation annotation
   */
  public void addAnnotation(BioCAnnotation annotation) {
    checkNotNull(annotation, "annotation cannot be null");
    checkArgument(annotations.containsKey(annotation.getID()));
    this.annotations.put(annotation.getID(), annotation);
  }

  /**
   * Adds relation in this passage.
   * 
   * @param relation relation
   */
  public void addRelation(BioCRelation relation) {
    checkNotNull(relation, "relation cannot be null");
    checkArgument(relations.containsKey(relation.getID()));
    this.relations.put(relation.getID(), relation);
  }

  /**
   * Adds sentence in this passage.
   * 
   * @param sentence sentence
   */
  public void addSentence(BioCSentence sentence) {
    checkNotNull(sentence, "sentence cannot be null");
    this.sentences.add(sentence);
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
    annotations.clear();
  }

  /**
   * Clears all sentences.
   */
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
   * 
   * @param annotationID id of a specified annotation
   * @return the annotation of the specified ID in this passage
   */
  public BioCAnnotation getAnnotation(String annotationID) {
    return annotations.get(annotationID);
  }

  /**
   * Annotations on the text of the passage.
   * 
   * @return annotations on the text of the passage
   */
  public Collection<BioCAnnotation> getAnnotations() {
    return annotations.values();
  }

  /**
   * Returns the value to which the specified key is mapped, or null if this
   * {@code infons} contains no mapping for the key.
   * 
   * @param key the key whose associated value is to be returned
   * @return the value to which the specified key is mapped
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
   * 
   * @return the information in the passage.
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
   * 
   * @return offset to where the passage begins
   */
  public int getOffset() {
    checkArgument(offset >= 0, "offset has to be >= 0");
    return offset;
  }

  /**
   * Returns the relation at the specified position in this passage.
   * 
   * @param relationID id of a specified relation
   * @return the relation of the specified ID in this passage
   */
  public BioCRelation getRelation(String relationID) {
    return relations.get(relationID);
  }

  /**
   * Relations between the annotations and possibly other relations on the text
   * of the passage.
   * 
   * @return relations of the passage
   */
  public Collection<BioCRelation> getRelations() {
    return relations.values();
  }

  /**
   * Returns the sentence at the specified position in this passage.
   * 
   * @param index sentence position in this passage
   * @return the sentence at the specified position in this passage
   */
  public BioCSentence getSentence(int index) {
    return sentences.get(index);
  }

  /**
   * Returns the number of sentences in this passage.
   * 
   * @return the number of sentences in this passage
   */
  public int getSentenceCount() {
    return sentences.size();
  }

  /**
   * The sentences of the passage.
   * 
   * @return sentences of the passage
   */
  public List<BioCSentence> getSentences() {
    return sentences;
  }

  /**
   * The original text of the passage.
   * 
   * @return the original text of the passage
   */
  public Optional<String> getText() {
    return Optional.ofNullable(text);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(offset, text, infons, sentences, annotations, relations);
  }

  /**
   * Associates the specified value with the specified key in this passage.
   * 
   * @param key key with which the specified value is to be associated
   * @param value value to be associated with the specified key
   */
  public void putInfon(String key, String value) {
    infons.put(key, value);
  }

  /**
   * Removes the value for a key from this passage if it is present (optional
   * operation).
   * 
   * @param key key with which the specified value is to be associated
   */
  public void removeInfon(String key) {
    infons.remove(key);
  }

  /**
   * Returns a unmodifiable iterator over the sentences in this passage in
   * proper sequence.
   * 
   * @return an iterator over the sentences in this passage in proper sequence
   */
  public Iterator<BioCSentence> sentenceIterator() {
    return sentences.iterator();
  }

  /**
   * Sets the information in this passage.
   * 
   * @param infons the information in this passage
   */
  public void setInfons(Map<String, String> infons) {
    clearInfons();
    this.infons.putAll(infons);
  }

  /**
   * Sets offset to where the passage begins.
   * 
   * @param offset to where the passage begins
   */
  public void setOffset(int offset) {
    this.offset = offset;
  }

  /**
   * Sets the sentences in this passage.
   * 
   * @param sentences the sentences in this passage
   */
  public void setSentences(List<BioCSentence> sentences) {
    clearSentences();
    this.sentences.addAll(sentences);
  }

  /**
   * Sets the original text of the passage.
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
        .append("sentences", sentences)
        .append("annotations", annotations)
        .append("relations", relations)
        .toString();
  }

}
