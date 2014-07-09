package org.biocreative.bioc;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The connection to the original text can be made through the {@code offset},
 * {@code length}, and possibly the {@code text} fields.
 */
public class BioCLocation {

  /**
   * Type of annotation. Options include "token", "noun phrase", "gene", and
   * "disease". The valid values should be described in the {@code key} file.
   */
  protected int offset;
  /**
   * The length of the annotated text. While unlikely, this could be zero to
   * describe an annotation that belongs between two characters.
   */
  protected int length;

  public BioCLocation() {
  }

  public BioCLocation(BioCLocation location) {
    this(location.offset, location.length);
  }

  public BioCLocation(int offset, int length) {
    setOffset(offset);
    setLength(length);
  }

  public int getLength() {
    return length;
  }

  public int getOffset() {
    return offset;
  }

  public void setLength(int length) {
    Validate.isTrue(length > 0, "length has to be greater than 0");
    this.length = length;
  }

  public void setOffset(int offset) {
    Validate.isTrue(offset > 0, "offset has to be greater than 0");
    this.offset = offset;
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
    return new ToStringBuilder(this).
        append("offset", offset).
        append("length", length).
        toString();
  }
}
