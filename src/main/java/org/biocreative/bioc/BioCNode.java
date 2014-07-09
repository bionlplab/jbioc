package org.biocreative.bioc;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BioCNode {

  /**
   * Id of an annotated object or another relation. Typically there will be one
   * label for each ref_id.
   */
  protected String refid;

  protected String role;

  public BioCNode() {
    refid = "";
    role = "";
  }

  public BioCNode(BioCNode node) {
    refid = node.refid;
    role = node.role;
  }

  public BioCNode(String refid, String role) {
    this.refid = refid;
    this.role = role;
  }

  public String getRefid() {
    return refid;
  }

  public String getRole() {
    return role;
  }

  public void setRefid(String refid) {
    this.refid = refid;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(refid)
        .append(role)
        .toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != getClass()) {
      return false;
    }
    BioCNode rhs = (BioCNode) obj;
    return new EqualsBuilder()
        .append(role, rhs.role)
        .append(refid, rhs.refid)
        .isEquals();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).
        append("refid", refid).
        append("rold", role).
        toString();
  }
}
