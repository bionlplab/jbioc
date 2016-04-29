package com.pengyifan.bioc;

public interface HasOffset {
  /**
   * The offset of the structure in the document. The significance of the exact value may depend on
   * the source corpus. They should be sequential and identify the passage and sentence position in
   * the document. Since Pubmed is extracted from an XML file, the title has an offset of zero,
   * while the abstract is assumed to begin after the title and one space.
   *
   * @return offset to where the structure begins
   */
  int getOffset();

  /**
   * Sets offset to where the structure begins.
   *
   * @param offset to where the structure begins
   */
  void setOffset(int offset);
}
