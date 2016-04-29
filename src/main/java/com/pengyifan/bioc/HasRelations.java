package com.pengyifan.bioc;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public interface HasRelations {

  /**
   * Adds relation in this structure.
   *
   * @param relation relation
   */
  default void addRelation(BioCRelation relation) {
    checkNotNull(relation, "relation cannot be null");
    checkArgument(!getRelation(relation.getID()).isPresent(), "Duplicated relation: %s", relation);
    getRelations().add(relation);
  }

  /**
   * Clears all relations.
   */
  default void clearRelations() {
    getRelations().clear();
  }

  /**
   * Returns the relation at the specified position in this structure.
   *
   * @param relationID id of a specified relation
   * @return the relation of the specified ID in this structure
   */
  default Optional<BioCRelation> getRelation(String relationID) {
    return getRelations().stream()
        .filter(r -> r.getID().equals(relationID))
        .findAny();
  }

  /**
   * Relations between the annotations and possibly other relations on the text of the structure.
   *
   * @return the relation of the structure
   */
  List<BioCRelation> getRelations();
}
