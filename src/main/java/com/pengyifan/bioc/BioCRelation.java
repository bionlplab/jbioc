package com.pengyifan.bioc;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Relationship between multiple {@link BioCAnnotation}s and possibly other
 * {@code BioCRelation}s.
 */
public class BioCRelation {

  private String id;
  private Map<String, String> infons;
  private Set<BioCNode> nodes;

  public BioCRelation() {
    infons = Maps.newHashMap();
    nodes = Sets.newHashSet();
  }

  public BioCRelation(BioCRelation relation) {
    this();
    setID(relation.id);
    setInfons(relation.infons);
    setNodes(relation.nodes);
  }

  public void addNode(BioCNode node) {
    checkNotNull(node, "node cannot be null");
    nodes.add(node);
  }

  /**
   * Clears all information.
   */
  public void clearInfons() {
    infons.clear();
  }

  /**
   * Clears all nodes in this relation.
   */
  public void clearNodes() {
    nodes.clear();
  }

  /**
   * Returns true if this relation contains the specified node.
   * @param node node whose presence in this relation is to be tested
   * @return if this relation contains the specified node
   */
  public boolean containsNode(BioCNode node) {
    return nodes.contains(node);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BioCRelation)) {
      return false;
    }
    BioCRelation rhs = (BioCRelation) obj;
    return Objects.equals(id, rhs.id)
        && Objects.equals(infons, rhs.infons)
        && Objects.equals(nodes, rhs.nodes);
  }

  /**
   * Returns the id used to refer to this relation in other relationships.
   * 
   * @return the id used to identify this annotation in a {@link BioCRelation}.
   */
  public String getID() {
    checkNotNull(id, "id cannot be null");
    return id;
  }

  /**
   * Returns the value to which the specified key is mapped, or null if this
   * {@code infons} contains no mapping for the key.
   * 
   * @param key the key whose associated value is to be returned
   * @return the value to which the specified key is mapped
   */
  public Optional<String> getInfon(String key) {
    return Optional.ofNullable(infons.get(key));
  }
  
  /**
   * Returns the information of relation. Implemented examples include
   * abbreviation long forms and short forms and protein events.
   * 
   * @return the information in this annotation.
   */
  public Map<String, String> getInfons() {
    return infons;
  }

  /**
   * Returns the number of nodes in this relation.
   * 
   * @return the number of nodes in this relation
   */
  public int getNodeCount() {
    return nodes.size();
  }

  /**
   * Returns nodes that describe how the referenced annotated object or other
   * relation participates in the current relationship.
   * 
   * @return nodes of the relation
   */
  public Set<BioCNode> getNodes() {
    return nodes;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, infons, nodes);
  }

  /**
   * Returns a unmodifiable iterator over the nodes in this relation in proper
   * sequence.
   * 
   * @return an iterator over the nodes in this relation in proper sequence
   */
  public Iterator<BioCNode> nodeIterator() {
    return nodes.iterator();
  }

  /**
   * Associates the specified value with the specified key in this relation.
   * 
   * @param key key with which the specified value is to be associated
   * @param value value to be associated with the specified key
   */
  public void putInfon(String key, String value) {
    infons.put(key, value);
  }

  /**
   * Removes the value for a key from this relation if it is present
   * (optional operation).
   * 
   * @param key key with which the specified value is to be associated
   */
  public void removeInfon(String key) {
    infons.remove(key);
  }

  /**
   * Sets the id used to identify this relation.
   * 
   * @param id the id used to identify this relation
   */
  public void setID(String id) {
    this.id = id;
  }

  /**
   * Sets the information in this relation.
   * 
   * @param infons the information in this relation
   */
  public void setInfons(Map<String, String> infons) {
    clearInfons();
    this.infons.putAll(infons);
  }

  /**
   * Sets the nodes in this relation.
   * 
   * @param nodes the nodes in this relation.
   */
  public void setNodes(Set<BioCNode> nodes) {
    clearNodes();
    this.nodes.addAll(nodes);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("infons", infons)
        .append("nodes", nodes)
        .toString();
  }
}
