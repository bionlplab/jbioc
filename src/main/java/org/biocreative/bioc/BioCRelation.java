package org.biocreative.bioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Relationship between multiple {@link BioCAnnotation}s and possibly other
 * {@code BioCRelation}s.
 */
public class BioCRelation implements Iterable<BioCNode> {

  /**
   * Used to refer to this relation in other relationships.
   */
  protected String              id;

  /**
   * Information of relation. Implemented examples include abbreviation long
   * forms and short forms and protein events.
   */
  protected Map<String, String> infons;

  /**
   * Describes how the referenced annotated object or other relation
   * participates in the current relationship.
   */
  protected List<BioCNode>      nodes;

  public BioCRelation() {
    id = "";
    infons = new HashMap<String, String>();
    nodes = new ArrayList<BioCNode>();
  }

  public BioCRelation(BioCRelation relation) {
    id = relation.id;
    infons = new HashMap<String, String>(relation.infons);
    nodes = new ArrayList<BioCNode>(relation.nodes);
  }

  public String getID() {
	    return id;
	  }
 
  public void setID(String id) {
	    this.id = id;
	  }

  public Map<String, String> getInfons() {
	    return infons;
	  }

  public void setInfons(Map<String, String> infons) {
	  this.infons = infons;
  }  

  public void clearInfons(){
	  infons.clear();
  }

  public String getInfon(String key) {
      return infons.get(key);
  }

  public void putInfon(String key, String value) {
	  infons.put(key, value);
  }

  public void removeInfon(String key){
	  infons.remove(key);
  }

  public void addNode(BioCNode node) {
    nodes.add(node);
  }
 
  public void addNode(String refId, String role) {
	    addNode(new BioCNode (refId, role));
  }
  
  public List<BioCNode> getNodes() {
    return nodes;
  }

  public void setNodes(List<BioCNode> nodes) {
    this.nodes = nodes;
  }

  @Override
  public Iterator<BioCNode> iterator() {
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
}
