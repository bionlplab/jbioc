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
  private ImmutableMap<String, String> infons;
  private ImmutableList<BioCDocument> documents;

  private BioCCollection() {
  }

  /**
   * The original source of the documents.
   */
  public String getSource() {
    return source;
  }

  /**
   * Date the documents obtained from the source.
   */
  public String getDate() {
    return date;
  }

  /**
   * Name of a file describing the contents and conventions used in this XML
   * file.
   */
  public String getKey() {
    return key;
  }

  /**
   * Returns the information in the collection.
   */
  public ImmutableMap<String, String> getInfons() {
    return infons;
  }

  /**
   * Returns the value to which the specified key is mapped, or null if this
   * {@code infons} contains no mapping for the key.
   */
  public Optional<String> getInfon(String key) {
    return Optional.fromNullable(infons.get(key));
  }

  /**
   * Returns all the documents in the collection. This will be empty if
   * document at a time IO is used to read the XML file. Any contents will be
   * ignored if written with document at a time IO.
   */
  public ImmutableList<BioCDocument> getDocuments() {
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
  public UnmodifiableIterator<BioCDocument> documentIterator() {
    return documents.iterator();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(source)
        .append(date)
        .append(key)
        .append(infons)
        .append(documents)
        .toHashCode();
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != getClass()) {
      return false;
    }
    BioCCollection rhs = (BioCCollection) obj;
    return new EqualsBuilder()
        .append(source, rhs.source)
        .append(infons, rhs.infons)
        .append(key, rhs.key)
        .append(date, rhs.date)
        .append(documents, rhs.documents)
        .isEquals();
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
      infons = new Hashtable<String, String>();
      documents = new ArrayList<BioCDocument>();
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

    public Builder setDocuments(List<BioCDocument> documents) {
      this.documents = new ArrayList<BioCDocument>(documents);
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
