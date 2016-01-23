package com.pengyifan.bioc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public abstract class BioCStructure implements BioCInfons {

  private Map<String, String> infons;
  private List<BioCAnnotation> annotations;
  private List<BioCRelation> relations;

  /**
   * Constructs an empty structure.
   */
  public BioCStructure() {
    infons = Maps.newHashMap();
    annotations = Lists.newArrayList();
    relations = Lists.newArrayList();
  }

  /**
   * Constructs a structure containing the information of the specified structure.
   *
   * @param structure the structure whose information is to be placed into this structure
   */
  public BioCStructure(BioCStructure structure) {
    this();
    infons.putAll(structure.infons);
    annotations.addAll(structure.annotations);
    relations.addAll(structure.relations);
  }

  /**
   * Adds annotation in this structure.
   *
   * @param annotation annotation
   */
  public void addAnnotation(BioCAnnotation annotation) {
    checkNotNull(annotation, "annotation cannot be null");
    checkArgument(!getAnnotation(annotation.getID()).isPresent(),
        "Duplicated annotation: %s", annotation);
    this.annotations.add(annotation);
  }


  /**
   * Adds relation in this structure.
   *
   * @param relation relation
   */
  public void addRelation(BioCRelation relation) {
    checkNotNull(relation, "relation cannot be null");
    checkArgument(!getRelation(relation.getID()).isPresent(), "Duplicated relation: %s", relation);
    this.relations.add(relation);
  }

  /**
   * Clears all annotations.
   */
  public void clearAnnotations() {
    annotations.clear();
  }


  /**
   * Clears all relations.
   */
  public void clearRelations() {
    relations.clear();
  }


  /**
   * Returns the annotation at the specified position in this structure.
   *
   * @param annotationID id of a specified annotation
   * @return the annotation of the specified ID in this document
   */
  public Optional<BioCAnnotation> getAnnotation(String annotationID) {
    return annotations.stream()
        .filter(a -> a.getID().equals(annotationID))
        .findFirst();
  }

  /**
   * Annotations on the text of the structure.
   *
   * @return annotations on the text of the structure
   */
  public Collection<BioCAnnotation> getAnnotations() {
    return annotations;
  }


  /**
   * Returns the information in the structure.
   *
   * @return the information in the structure
   */
  public Map<String, String> getInfons() {
    return infons;
  }

  /**
   * Returns the relation at the specified position in this structure.
   *
   * @param relationID id of a specified relation
   * @return the relation of the specified ID in this structure
   */
  public Optional<BioCRelation> getRelation(String relationID) {
    return relations.stream()
        .filter(r -> r.getID().equals(relationID))
        .findAny();
  }

  /**
   * Relations between the annotations and possibly other relations on the text
   * of the document.
   *
   * @return relations of the structure
   */
  public Collection<BioCRelation> getRelations() {
    return relations;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BioCStructure)) {
      return false;
    }
    BioCStructure rhs = (BioCStructure) obj;
    return Objects.equals(infons, rhs.infons)
        && Objects.equals(annotations, rhs.annotations)
        && Objects.equals(relations, rhs.relations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(infons, annotations, relations);
  }
}
