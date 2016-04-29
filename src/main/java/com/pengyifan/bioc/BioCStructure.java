package com.pengyifan.bioc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public abstract class BioCStructure implements HasInfons, HasAnnotations, HasRelations, BioCObject {

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


  @Override
  public List<BioCAnnotation> getAnnotations() {
    return annotations;
  }

  @Override
  public Map<String, String> getInfons() {
    return infons;
  }

  @Override
  public List<BioCRelation> getRelations() {
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
