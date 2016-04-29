package com.pengyifan.bioc;

import java.util.Optional;

public interface HasText {

  /**
   * Returns the original text
   *
   * @return the original text
   */
  Optional<String> getText();
  /**
   * Sets the original text.
   *
   * @param text the original text
   */
  void setText(String text);
}
