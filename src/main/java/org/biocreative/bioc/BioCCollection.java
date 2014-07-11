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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;

/**
 * Collection of documents.
 * 
 * Collection of documents for a project. They may be an entire corpus or some
 * portion of a corpus. Fields are provided to describe the collection.
 * 
 * Documents may appear empty if doing document at a time IO.
 */
public class BioCCollection {

  /**
   * Describe the original source of the documents.
   */
  private String source;

  /**
   * Date the documents obtained from the source.
   */
  private String date;

  /**
   * Name of a file describing the contents and conventions used in this XML
   * file.
   */
  private String key;
  private ImmutableMap<String, String> infons;

  /**
   * All the documents in the collection. This will be empty if document at a
   * time IO is used to read the XML file. Any contents will be ignored if
   * written with document at a time IO.
   */
  private ImmutableList<BioCDocument> documents;

  private BioCCollection() {
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getDate() {
    return date;
  }

  public String getKey() {
    return key;
  }

  public ImmutableMap<String, String> getInfons() {
    return infons;
  }

  public String getInfon(String key) {
    return infons.get(key);
  }

  public ImmutableList<BioCDocument> getDocuments() {
    return documents;
  }

  public int getDocmentCount() {
    return documents.size();
  }

  public BioCDocument getDocument(int index) {
    return documents.get(index);
  }

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

  public static Builder newBuilder() {
    return new Builder();
  }

  public Builder getBuilder() {
    Builder builder = newBuilder()
        .setSource(source)
        .setDate(date)
        .setInfons(infons)
        .setDocuments(documents);
    return builder;
  }

  public static class Builder {

    private String source;
    private String date;
    private String key;
    private Map<String, String> infons;
    private List<BioCDocument> documents;

    private Builder() {
      infons = new HashMap<String, String>();
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

    public Builder setDocuments(List<BioCDocument> documents) {
      this.documents = new ArrayList<BioCDocument>(documents);
      return this;
    }

    public Builder addDocument(BioCDocument document) {
      this.documents.add(document);
      return this;
    }

    public Builder clearDocuments() {
      documents.clear();
      return this;
    }

    public Builder clear() {
      return clearSource()
          .clearDate()
          .clearKey()
          .clearDocuments();
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
