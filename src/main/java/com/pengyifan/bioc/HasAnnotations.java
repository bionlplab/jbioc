package com.pengyifan.bioc;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public interface HasAnnotations {

  /**
   * Adds annotation in this structure.
   *
   * @param annotation annotation
   */
  default void addAnnotation(BioCAnnotation annotation) {
    checkNotNull(annotation, "annotation cannot be null");
    checkArgument(!getAnnotation(annotation.getID()).isPresent(),
        "Duplicated annotation: %s", annotation);
    getAnnotations().add(annotation);
  }

  /**
   * Clears all annotations.
   */
  default void clearAnnotations() {
    getAnnotations().clear();
  }

  /**
   * Returns the annotation at the specified ID in this structure.
   *
   * @param annotationID id of a specified annotation
   * @return the annotation of the specified ID in this structure
   */
  default Optional<BioCAnnotation> getAnnotation(String annotationID) {
    return getAnnotations().stream()
        .filter(a -> a.getID().equals(annotationID))
        .findFirst();
  }

  /**
   * Returns the annotation of the specified role of the relation in this structure.
   *
   * @param relation the specified relation
   * @param role     the specified role in the relation
   * @return the annotation of the specified role of the relation in this structure
   * @throws java.util.NoSuchElementException if there is no role in the relation
   */
  default Optional<BioCAnnotation> getAnnotation(BioCRelation relation, String role) {
    try {
      return getAnnotation(relation.getNode(role).get().getRefid());
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException(
          String.format("Cannot find %s in relation [%s]", role, relation));
    }
  }
  /**
   * Annotations on the text of the structure.
   *
   * @return annotations on the text of the structure
   */
  List<BioCAnnotation> getAnnotations();
}
