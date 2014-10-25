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

  private BioCCollection() {
  }

  /**
   * Returns the original source of the documents.
   */
  public String getSource() {
    return source;
  }

  /**
   * Returns date the documents obtained from the source.
   */
  public String getDate() {
    return date;
  }

  /**
   * Returns name of a file describing the contents and conventions used in
   * this XML file.
   */
  public String getKey() {
    return key;
  }

  /**
   * Returns the information in the collection.
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
   * Returns all the documents in the collection. This will be empty if
   * document at a time IO is used to read the XML file. Any contents will be
   * ignored if written with document at a time IO.
   */
  public List<BioCDocument> getDocuments() {
    return documents;
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
   * Returns a unmodifiable iterator over the document in this collection in
   * proper sequence.
   */
  public Iterator<BioCDocument> documentIterator() {
    return documents.iterator();
  }

  @Override
  public int hashCode() {
    return Objects.hash(source, date, key, infons, documents);
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

  /**
   * Constructs a new builder. Use this to derive a new collection.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Constructs a builder initialized with the current collection. Use this to
   * derive a new collection from the current one.
   */
  public Builder toBuilder() {
    return newBuilder()
        .setSource(source)
        .setDate(date)
        .setInfons(infons)
        .setKey(key)
        .setDocuments(documents);
  }

  public static class Builder {

    private String source;
    private String date;
    private String key;
    private Map<String, String> infons;
    private List<BioCDocument> documents;

    private Builder() {
      infons = Maps.newHashMap();
      documents = Lists.newArrayList();
    }

    public Builder setSource(String source) {
      Validate.notNull(source, "source cannot be null");
      this.source = source;
      return this;
    }

    public Builder setDate(String date) {
      Validate.notNull(date, "date cannot be null");
      this.date = date;
      return this;
    }

    public Builder setKey(String key) {
      Validate.notNull(key, "key cannot be null");
      this.key = key;
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

    public Builder setDocuments(List<BioCDocument> documents) {
      this.documents = Lists.newArrayList(documents);
      return this;
    }

    public Builder addDocument(BioCDocument document) {
      Validate.notNull(document, "document cannot be null");
      this.documents.add(document);
      return this;
    }

    public Builder clearDocuments() {
      documents.clear();
      return this;
    }

    public Builder clearSource() {
      source = null;
      return this;
    }

    public Builder clearDate() {
      date = null;
      return this;
    }

    public Builder clearKey() {
      key = null;
      return this;
    }

    public BioCCollection build() {
      checkArguments();

      BioCCollection result = new BioCCollection();
      result.source = source;
      result.date = date;
      result.key = key;
      result.infons = ImmutableMap.copyOf(infons);
      result.documents = ImmutableList.copyOf(documents);

      return result;
    }

    private void checkArguments() {
      Validate.isTrue(source != null, "source has to be set");
      Validate.isTrue(date != null, "date has to be set");
      Validate.isTrue(key != null, "key has to be set");
    }
  }
}
