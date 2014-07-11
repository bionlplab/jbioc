package org.biocreative.bioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;

/**
 * Relationship between multiple {@link BioCAnnotation}s and possibly other
 * {@code BioCRelation}s.
 */
public class BioCRelation {

  /**
   * Used to refer to this relation in other relationships.
   */
  private String id;

  /**
   * Information of relation. Implemented examples include abbreviation long
   * forms and short forms and protein events.
   */
  private ImmutableMap<String, String> infons;

  /**
   * Describes how the referenced annotated object or other relation
   * participates in the current relationship.
   */
  private ImmutableList<BioCNode> nodes;

  private BioCRelation() {
  }

  public String getID() {
    return id;
  }

  public ImmutableMap<String, String> getInfons() {
    return infons;
  }

  public String getInfon(String key) {
    return infons.get(key);
  }

  public ImmutableList<BioCNode> getNodes() {
    return nodes;
  }

  public BioCNode getNode(int index) {
    return nodes.get(index);
  }

  public int getNodeCount() {
    return nodes.size();
  }

  public UnmodifiableIterator<BioCNode> nodeIterator() {
    return nodes.iterator();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(id)
        .append(infons)
        .append(nodes)
        .toHashCode();
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != getClass()) {
      return false;
    }
    BioCRelation rhs = (BioCRelation) obj;
    return new EqualsBuilder()
        .append(id, rhs.id)
        .append(infons, rhs.infons)
        .append(nodes, rhs.nodes)
        .isEquals();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("infons", infons)
        .append("nodes", nodes)
        .toString();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Builder getBuilder() {
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
      infons = new HashMap<String, String>();
      nodes = new ArrayList<BioCNode>();
    }

    public Builder setID(String id) {
      Validate.notNull(id, "id cannot be null");
      this.id = id;
      return this;
    }

    public Builder setInfons(Map<String, String> infons) {
      this.infons = new HashMap<String, String>(infons);
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

    public Builder clear() {
      id = null;
      clearInfons();
      clearNodes();
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
      nodes.add(node);
      return this;
    }

    public Builder setNodes(List<BioCNode> nodes) {
      this.nodes = new ArrayList<BioCNode>(nodes);
      return this;
    }

    public Builder addNode(String refId, String role) {
      return addNode(BioCNode.newBuilder()
          .setRefid(refId)
          .setRole(role)
          .build());
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
      Validate.isTrue(id != null, "id has to be set");
      Validate.isTrue(!nodes.isEmpty(), "there must be some nodes");
    }
  }
}
