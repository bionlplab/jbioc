package com.pengyifan.bioc;

public interface HasID {
  /**
   * Returns the id used to identify this structure in a {@link BioCRelation}.
   *
   * @return the id used to identify this structure in a {@link BioCRelation}.
   */
  String getID();

  /**
   * Sets the id used to identify this structure in a {@link BioCRelation}.
   *
   * @param id the id used to identify this structure in a {@link BioCRelation}
   */
  void setID(String id);
}
