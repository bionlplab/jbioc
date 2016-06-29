package com.pengyifan.bioc;

import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The connection to the original text can be made through the {@code offset} and {@code length}
 * fields.
 *
 * @author Yifan Peng
 * @since 1.0.0
 */
public class BioCLocation implements HasOffset, BioCObject {

  private Integer offset;
  private Integer length;

  /**
   * Constructs a newly <code>BioCLocation</code> object that has offset and length.
   *
   * @param offset the offset of annotation
   * @param length the length of the annotated text
   */
  public BioCLocation(int offset, int length) {
    this.offset = offset;
    this.length = length;
  }

  /**
   * Constructs a location containing the information of the specified location.
   *
   * @param location the location whose information is to be placed into this location
   */
  public BioCLocation(BioCLocation location) {
    this(location.offset, location.length);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BioCLocation)) {
      return false;
    }
    BioCLocation rhs = (BioCLocation) obj;
    return Objects.equals(offset, rhs.offset)
        && Objects.equals(length, rhs.length);
  }

  /**
   * Returns the length of the annotated text. While unlikely, this could be zero to describe an
   * annotation that belongs between two characters.
   *
   * @return the length of the annotated text
   */
  public int getLength() {
    checkArgument(length > 0, "length has to be > 0");
    return length;
  }

  @Override
  public int getOffset() {
    checkArgument(offset >= 0, "offset has to be >= 0");
    return offset;
  }

  public int getEnd() {
    return offset + length;
  }

  @Override
  public int hashCode() {
    return Objects.hash(offset, length);
  }

  /**
   * Sets the length of the annotated text.
   *
   * @param length the length of the annotated text
   */
  public void setLength(int length) {
    this.length = length;
  }

  @Override
  public void setOffset(int offset) {
    this.offset = offset;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
        append("offset", offset).
        append("length", length).
        toString();
  }

}
