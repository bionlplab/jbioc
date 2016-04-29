package com.pengyifan.bioc;

import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class BioCStructureWithText extends BioCStructure implements HasText, HasOffset {
  private int offset;
  private String text;

  /**
   * Constructs an empty structure.
   */
  public BioCStructureWithText() {
    super();
    offset = -1;
    text = null;
  }

  /**
   * Constructs a passage containing the information of the specified structure.
   *
   * @param structure the passage whose information is to be placed into this structure
   */
  public BioCStructureWithText(BioCStructureWithText structure) {
    super(structure);
    setOffset(structure.offset);
    setText(structure.text);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BioCStructureWithText)) {
      return false;
    }
    BioCStructureWithText rhs = (BioCStructureWithText) obj;
    return super.equals(rhs)
        && Objects.equals(text, rhs.text)
        && Objects.equals(offset, rhs.offset);
  }

  @Override
  public int getOffset() {
    checkArgument(offset >= 0, "offset has to be >= 0");
    return offset;
  }

  @Override
  public Optional<String> getText() {
    return Optional.ofNullable(text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), offset, text);
  }


  @Override
  public void setOffset(int offset) {
    this.offset = offset;
  }


  @Override
  public void setText(String text) {
    this.text = text;
  }
}
