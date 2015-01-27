package org.biocreative.bioc;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The annotations and/or other relations in the relation.
 */
public class BioCNode {

  private String refid;
  private String role;

  public BioCNode() {
  }

  public BioCNode(String refid, String role) {
    this.refid = refid;
    this.role = role;
  }

  /**
   * Returns the id of an annotated object or another relation. Typically there
   * will be one label for each node.
   */
  public String getRefid() {
    checkNotNull(refid, "refid has to be set");
    return refid;
  }

  /**
   * Returns the role of how the referenced annotation or other relation
   * participates in the current relation.
   */
  public String getRole() {
    checkNotNull(role, "role has to be set");
    return role;
  }

  @Override
  public int hashCode() {
    return Objects.hash(refid, role);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BioCNode)) {
      return false;
    }
    BioCNode rhs = (BioCNode) obj;
    return Objects.equals(role, rhs.role)
        && Objects.equals(refid, rhs.refid);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
        append("refid", refid).
        append("role", role).
        toString();
  }

  public void setRefid(String refid) {
    this.refid = refid;
  }

  public void setRole(String role) {
    this.role = role;
  }

}
