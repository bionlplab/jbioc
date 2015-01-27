package org.biocreative.bioc;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Relationship between multiple {@link BioCAnnotation}s and possibly other
 * {@code BioCRelation}s.
 */
public class BioCRelation {

  private String id;
  private Map<String, String> infons;
  private List<BioCNode> nodes;

  public BioCRelation() {
    infons = Maps.newHashMap();
    nodes = Lists.newArrayList();
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

  public void clearInfons() {
    infons.clear();
  }

  public void clearNodes() {
    nodes.clear();
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
   */
  public String getID() {
    checkNotNull(id, "id cannot be null");
    return id;
  }

  /**
   * Returns the value to which the specified key is mapped, or null if this
   * {@code infons} contains no mapping for the key.
   */
  public Optional<String> getInfon(String key) {
    return Optional.ofNullable(infons.get(key));
  }

  /**
   * Returns the information of relation. Implemented examples include
   * abbreviation long forms and short forms and protein events.
   */
  public Map<String, String> getInfons() {
    return infons;
  }

  /**
   * Returns the node at the specified position in this relation.
   */
  public BioCNode getNode(int index) {
    return nodes.get(index);
  }

  /**
   * Returns the number of nodes in this relation.
   */
  public int getNodeCount() {
    return nodes.size();
  }

  /**
   * Returns nodes that describe how the referenced annotated object or other
   * relation participates in the current relationship.
   */
  public List<BioCNode> getNodes() {
    return nodes;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, infons, nodes);
  }

  /**
   * Returns a unmodifiable iterator over the nodes in this relation in proper
   * sequence.
   */
  public Iterator<BioCNode> nodeIterator() {
    return nodes.iterator();
  }

  public void putInfon(String key, String value) {
    infons.put(key, value);
  }

  public void removeInfon(String key) {
    infons.remove(key);
  }

  public void setID(String id) {
    this.id = id;
  }

  public void setInfons(Map<String, String> infons) {
    clearInfons();
    this.infons.putAll(infons);
  }

  public void setNodes(List<BioCNode> nodes) {
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
