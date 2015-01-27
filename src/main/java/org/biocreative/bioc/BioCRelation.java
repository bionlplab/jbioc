package org.biocreative.bioc;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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

  private BioCRelation() {
  }

  /**
   * Returns the id used to refer to this relation in other relationships.
   */
  public String getID() {
    return id;
  }

  /**
   * Returns the information of relation. Implemented examples include
   * abbreviation long forms and short forms and protein events.
   */
  public Map<String, String> getInfons() {
    return infons;
  }

  /**
   * Returns the value to which the specified key is mapped, or null if this
   * {@code infons} contains no mapping for the key.
   */
  public Optional<String> getInfon(String key) {
    return Optional.ofNullable(infons.get(key));
  }

  /**
   * Returns nodes that describe how the referenced annotated object or other
   * relation participates in the current relationship.
   */
  public List<BioCNode> getNodes() {
    return nodes;
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
   * Returns a unmodifiable iterator over the nodes in this relation in proper
   * sequence.
   */
  public Iterator<BioCNode> nodeIterator() {
    return nodes.iterator();
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, infons, nodes);
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

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("infons", infons)
        .append("nodes", nodes)
        .toString();
  }

  /**
   * Constructs a new builder. Use this to derive a new relation.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Constructs a builder initialized with the current relation. Use this to
   * derive a new relation from the current one.
   */
  public Builder toBuilder() {
    return newBuilder()
        .setID(id)
        .setInfons(infons)
        .setNodes(nodes);
  }

  public static class Builder {

    private String id;
    private Map<String, String> infons;
    private List<BioCNode> nodes;

    private Builder() {
      infons = Maps.newHashMap();
      nodes = Lists.newArrayList();
    }

    public Builder setID(String id) {
      Validate.notNull(id, "id cannot be null");
      this.id = id;
      return this;
    }

    public Builder setInfons(Map<String, String> infons) {
      this.infons = Maps.newHashMap(infons);
      return this;
    }

    public Builder clearInfons() {
      infons.clear();
      return this;
    }

    public Builder clearNodes() {
      nodes.clear();
      return this;
    }

    public Builder putInfon(String key, String value) {
      infons.put(key, value);
      return this;
    }

    public Builder removeInfon(String key) {
      infons.remove(key);
      return this;
    }

    public Builder addNode(BioCNode node) {
      Validate.notNull(node, "node cannot be null");
      nodes.add(node);
      return this;
    }

    public Builder setNodes(List<BioCNode> nodes) {
      this.nodes = Lists.newArrayList(nodes);
      return this;
    }

    public Builder addNode(String refid, String role) {
      return addNode(new BioCNode(refid, role));
    }

    public BioCRelation build() {
      checkArguments();

      BioCRelation result = new BioCRelation();
      result.id = id;
      result.infons = ImmutableMap.copyOf(infons);
      result.nodes = ImmutableList.copyOf(nodes);
      return result;
    }

    private void checkArguments() {
      Validate.notNull(id, "id has to be set");
      Validate.notEmpty(nodes, "there must be some nodes");
    }
  }
}
