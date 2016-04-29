package com.pengyifan.bioc;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * One sentence in a {@link BioCPassage}.
 * <p>
 * It may contain the original text of the sentence or it might be {@link BioCAnnotation}s and
 * possibly {@link BioCRelation}s on the text of the passage.
 * <p>
 * There is no code to keep those possibilities mutually exclusive. However the currently available
 * DTDs only describe the listed possibilities.
 *
 * @author Yifan Peng
 * @since 1.0.0
 */
public class BioCSentence extends BioCStructureWithText {

  /**
   * Constructs an empty sentence.
   */
  public BioCSentence() {
    super();
  }

  /**
   * Constructs a sentence containing the information of the specified sentence.
   *
   * @param sentence the sentence whose information is to be placed into this passage
   */
  public BioCSentence(BioCSentence sentence) {
    super(sentence);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BioCSentence)) {
      return false;
    }
    BioCSentence rhs = (BioCSentence) obj;
    return super.equals(rhs);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("offset", getOffset())
        .append("text", getText())
        .append("infons", getInfons())
        .append("annotations", getAnnotations())
        .append("ie", getRelations())
        .toString();
  }
}
