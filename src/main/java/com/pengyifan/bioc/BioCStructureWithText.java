package com.pengyifan.bioc;

import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class BioCStructureWithText extends BioCStructure {
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
   * @param structure the passage whose information is to be placed into this
   *                  structure
   */
  public BioCStructureWithText(BioCStructureWithText structure) {
    super(structure);
    setOffset(structure.offset);
    setText(structure.text);
  }

  /**
   * The offset of the passage in the parent document. The significance of the
   * exact value may depend on the source corpus. They should be sequential and
   * identify the passage's position in the document. Since Pubmed is extracted
   * from an XML file, the title has an offset of zero, while the abstract is
   * assumed to begin after the title and one space.
   *
   * @return offset to where the passage begins
   */
  public int getOffset() {
    checkArgument(offset >= 0, "offset has to be >= 0");
    return offset;
  }

  /**
   * Returns the original text of the passage.
   *
   * @return the original text of the passage
   */
  public Optional<String> getText() {
    return Optional.ofNullable(text);
  }


  /**
   * Sets offset to where the passage begins.
   *
   * @param offset to where the passage begins
   */
  public void setOffset(int offset) {
    this.offset = offset;
  }


  /**
   * Sets the original text of the passage.
   *
   * @param text the original text
   */
  public void setText(String text) {
    this.text = text;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), offset, text);
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
}
