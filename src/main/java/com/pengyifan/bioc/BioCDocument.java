package com.pengyifan.bioc;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * One document in the {@link BioCCollection}.
 * <p>
 * An id, typically from the original corpus, identifies the particular
 * document. It includes {@link BioCPassage}s in the document and possibly
 * {@link BioCRelation}s over annotations on the document.
 *
 * @author Yifan Peng
 * @since 1.0.0
 */
public class BioCDocument extends BioCStructure {

  private String id;
  private List<BioCPassage> passages;

  /**
   * Constructs an empty document.
   */
  public BioCDocument() {
    this((String) null);
  }

  /**
   * Constructs an empty document with id.
   *
   * @param id the id used to identify document
   */
  public BioCDocument(String id) {
    super();
    this.id = id;
    passages = Lists.newArrayList();
  }

  /**
   * Constructs an document containing the information of the specified
   * document.
   *
   * @param document bioc document
   */
  public BioCDocument(BioCDocument document) {
    super(document);
    this.id = document.id;
    passages = Lists.newArrayList(document.passages);
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
   * Clears all passages.
   */
  public void clearPassages() {
    passages.clear();
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
    return super.equals(rhs)
        && Objects.equals(id, rhs.id)
        && Objects.equals(passages, rhs.passages);
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

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, passages);
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
        .append("infons", getInfons())
        .append("passages", passages)
        .append("annotations", getAnnotations())
        .append("relations", getRelations())
        .toString();
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
   * Sets the id used to identify this structure.
   *
   * @param id the id used to identify structure
   */
  public void setID(String id) {
    this.id = id;
  }
}
