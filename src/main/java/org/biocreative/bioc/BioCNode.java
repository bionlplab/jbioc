package org.biocreative.bioc;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BioCNode {

  /**
   * Id of an annotated object or another relation. Typically there will be one
   * label for each ref_id.
   */
  private String refid;

  private String role;

  private BioCNode() {
  }

  public String getRefid() {
    return refid;
  }

  public String getRole() {
    return role;
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
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
        append("refid", refid).
        append("role", role).
        toString();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Builder getBuilder() {
    return newBuilder()
        .setRefid(refid)
        .setRole(role);
  }

  public static class Builder {

    private String refid;
    private String role;

    private Builder() {
    }

    public Builder setRefid(String refid) {
      Validate.notNull(refid, "refid cannot be null");
      this.refid = refid;
      return this;
    }

    public Builder setRole(String role) {
      Validate.notNull(role, "role cannot be null");
      this.role = role;
      return this;
    }
    
    public Builder clearRefid() {
      this.refid = null;
      return this;
    }

    public Builder clearRole() {
      this.role = null;
      return this;
    }

    public BioCNode build() {
      checkArguments();

      BioCNode result = new BioCNode();
      result.refid = refid;
      result.role = role;
      return result;
    }

    private void checkArguments() {
      Validate.isTrue(refid != null, "refid has to be set");
      Validate.isTrue(role != null, "role has to be set");
    }
  }
}
