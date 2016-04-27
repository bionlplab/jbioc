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
 * 
 * @since 1.0.0
 * @author Yifan Peng
 */
public class BioCPassage extends BioCStructureWithText {

  private List<BioCSentence> sentences;

  /**
   * Constructs an empty passage.
   */
  public BioCPassage() {
    super();
    sentences = Lists.newArrayList();
  }

  /**
   * Constructs a passage containing the information of the specified passage.
   * 
   * @param passage the passage whose information is to be placed into this
   *          passage
   */
  public BioCPassage(BioCPassage passage) {
    super(passage);
    sentences = Lists.newArrayList(passage.sentences);
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
    return super.equals(rhs)
        && Objects.equals(sentences, rhs.sentences);
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



  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), sentences);
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
   * Sets the sentences in this passage.
   * 
   * @param sentences the sentences in this passage
   */
  public void setSentences(List<BioCSentence> sentences) {
    clearSentences();
    this.sentences.addAll(sentences);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("offset", getOffset())
        .append("text", getText())
        .append("infons", getInfons())
        .append("sentences", sentences)
        .append("annotations", getAnnotations())
        .append("ie", getRelations())
        .toString();
  }

}
