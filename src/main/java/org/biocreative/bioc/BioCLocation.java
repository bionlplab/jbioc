package org.biocreative.bioc;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.biocreative.bioc.BioCSentence.Builder;

/**
 * The connection to the original text can be made through the {@code offset},
 * {@code length}, and possibly the {@code text} fields.
 */
public class BioCLocation {

  /**
   * Type of annotation. Options include "token", "noun phrase", "gene", and
   * "disease". The valid values should be described in the {@code key} file.
   */
  private int offset;
  /**
   * The length of the annotated text. While unlikely, this could be zero to
   * describe an annotation that belongs between two characters.
   */
  private int length;

  private BioCLocation() {
  }

  public int getLength() {
    return length;
  }

  public int getOffset() {
    return offset;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(offset)
        .append(length)
        .toHashCode();
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != getClass()) {
      return false;
    }
    BioCLocation rhs = (BioCLocation) obj;
    return new EqualsBuilder()
        .append(offset, rhs.offset)
        .append(length, rhs.length)
        .isEquals();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
        append("offset", offset).
        append("length", length).
        toString();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Builder getBuilder() {
    return newBuilder()
        .setLength(length)
        .setOffset(offset);
  }

  public static class Builder {

    private int offset;
    private int length;

    private Builder() {
      offset = -1;
      length = -1;
    }

    public Builder setLength(int length) {
      Validate.isTrue(length > 0, "length has to be > 0");
      this.length = length;
      return this;
    }

    public Builder setOffset(int offset) {
      Validate.isTrue(offset >= 0, "offset has to be >= 0");
      this.offset = offset;
      return this;
    }
    
    public Builder clearOffset() {
      offset = -1;
      return this;
    }
    
    public Builder clearLength() {
      length = -1;
      return this;
    }
    
    public Builder clear() {
      return clearOffset()
          .clearLength();
    }

    public BioCLocation build() {
      checkArguments();

      BioCLocation result = new BioCLocation();
      result.offset = offset;
      result.length = length;
      return result;
    }

    private void checkArguments() {
      Validate.isTrue(offset != -1, "offset has to be set");
      Validate.isTrue(length != -1, "length has to be set");
    }
  }
}
