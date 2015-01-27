package org.biocreative.bioc;

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
 * Collection of documents.
 * <p>
 * Collection of documents for a project. They may be an entire corpus or some
 * portion of a corpus. Fields are provided to describe the collection.
 * <p>
 * Documents may appear empty if doing document at a time IO.
 */
public class BioCCollection {

  private String source;
  private String date;
  private String key;
  private Map<String, String> infons;
  private List<BioCDocument> documents;

  public BioCCollection() {
    infons = Maps.newHashMap();
    documents = Lists.newArrayList();
  }

  public BioCCollection(BioCCollection collection) {
    this();
    setSource(collection.source);
    setDate(collection.date);
    setInfons(collection.infons);
    setKey(collection.key);
    setDocuments(collection.documents);
  }

  public void addDocument(BioCDocument document) {
    checkNotNull(document, "document cannot be null");
    this.documents.add(document);
  }

  public void clearDocuments() {
    documents.clear();
  }

  public void clearInfons() {
    infons.clear();
  }

  /**
   * Returns a unmodifiable iterator over the document in this collection in
   * proper sequence.
   */
  public Iterator<BioCDocument> documentIterator() {
    return documents.iterator();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BioCCollection)) {
      return false;
    }
    BioCCollection rhs = (BioCCollection) obj;
    return Objects.equals(source, rhs.source)
        && Objects.equals(infons, rhs.infons)
        && Objects.equals(key, rhs.key)
        && Objects.equals(date, rhs.date)
        && Objects.equals(documents, rhs.documents);
  }

  /**
   * Returns date the documents obtained from the source.
   */
  public String getDate() {
    checkNotNull(date, "date cannot be null");
    return date;
  }

  /**
   * Returns the document at the specified position in this collection.
   */
  public int getDocmentCount() {
    return documents.size();
  }

  /**
   * Returns the document at the specified position in this collection.
   */
  public BioCDocument getDocument(int index) {
    return documents.get(index);
  }

  /**
   * Returns all the documents in the collection. This will be empty if
   * document at a time IO is used to read the XML file. Any contents will be
   * ignored if written with document at a time IO.
   */
  public List<BioCDocument> getDocuments() {
    return documents;
  }

  /**
   * Returns the value to which the specified key is mapped, or null if this
   * {@code infons} contains no mapping for the key.
   */
  public Optional<String> getInfon(String key) {
    return Optional.ofNullable(infons.get(key));
  }

  /**
   * Returns the information in the collection.
   */
  public Map<String, String> getInfons() {
    return infons;
  }

  /**
   * Returns name of a file describing the contents and conventions used in
   * this XML file.
   */
  public String getKey() {
    checkNotNull(key, "key cannot be null");
    return key;
  }

  /**
   * Returns the original source of the documents.
   */
  public String getSource() {
    checkNotNull(source, "source cannot be null");
    return source;
  }

  @Override
  public int hashCode() {
    return Objects.hash(source, date, key, infons, documents);
  }

  public void putInfon(String key, String value) {
    infons.put(key, value);
  }

  public void removeInfon(String key) {
    infons.remove(key);
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setDocuments(List<BioCDocument> documents) {
    clearDocuments();
    this.documents.addAll(documents);
  }

  public void setInfons(Map<String, String> infons) {
    clearInfons();
    this.infons.putAll(infons);
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setSource(String source) {
    this.source = source;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("source", source)
        .append("date", date)
        .append("key", key)
        .append("infons", infons)
        .append("documents", documents)
        .toString();
  }

}
