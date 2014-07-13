package org.biocreative.bioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.Relation;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;

/**
 * Stand off annotation. The connection to the original text can be made
 * through the {@code location} and the {@code text} fields.
 */
public class BioCAnnotation {

  /**
   * Id used to identify this annotation in a {@link Relation}.
   */
  private String id;
  private ImmutableMap<String, String> infons;
  private ImmutableList<BioCLocation> locations;

  /**
   * The annotated text.
   */
  private String text;

  private BioCAnnotation() {
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

  public ImmutableList<BioCLocation> getLocations() {
    return locations;
  }
  
  public BioCLocation getLocation(int i) {
    return locations.get(i);
  }
  
  public int getLocationCount() {
    return locations.size();
  }
  
  public UnmodifiableIterator<BioCLocation> locationIterator() {
    return locations.iterator();
  }

  public String getText() {
    return text;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(id)
        .append(text)
        .append(infons)
        .append(locations)
        .toHashCode();
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != getClass()) {
      return false;
    }
    BioCAnnotation rhs = (BioCAnnotation) obj;
    return new EqualsBuilder()
        .append(id, rhs.id)
        .append(text, rhs.text)
        .append(infons, rhs.infons)
        .append(locations, rhs.locations)
        .isEquals();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("id", id)
        .append("text", text)
        .append("infons", infons)
        .append("locations", locations)
        .toString();
  }
  
  public Builder getBuilder() {
    return newBuilder()
        .setID(id)
        .setText(text)
        .setInfons(infons)
        .setLocations(locations);
  }
  
  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {

    private String id;
    private String text;
    private Map<String, String> infons;
    private List<BioCLocation> locations;

    private Builder() {
      infons = new HashMap<String, String>();
      locations = new ArrayList<BioCLocation>();
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

    public Builder clearLocations() {
      locations.clear();
      return this;
    }

    public Builder clear() {
      return clearInfons()
          .clearLocations()
          .clearID()
          .clearText();
    }

    public Builder putInfon(String key, String value) {
      infons.put(key, value);
      return this;
    }

    public Builder removeInfon(String key) {
      infons.remove(key);
      return this;
    }

    public Builder addLocation(BioCLocation location) {
      locations.add(location);
      return this;
    }

    public Builder setLocations(List<BioCLocation> locations) {
      this.locations = new ArrayList<BioCLocation>(locations);
      return this;
    }

    public Builder addLocation(int offset, int length) {
      return addLocation(BioCLocation.newBuilder()
          .setOffset(offset)
          .setLength(length)
          .build());
    }
    
    public Builder setText(String text) {
      Validate.notNull(text, "text cannot be null");
      this.text = text;
      return this;
    }
    
    public Builder clearText() {
      text = null;
      return this;
    }
    
    public Builder clearID() {
      id = null;
      return this;
    }

    public BioCAnnotation build() {
      checkArguments();

      BioCAnnotation result = new BioCAnnotation();
      result.id = id;
      result.text = text;
      result.infons = ImmutableMap.copyOf(infons);
      result.locations = ImmutableList.copyOf(locations);
      return result;
    }

    private void checkArguments() {
      Validate.isTrue(id != null, "id has to be set");
      Validate.isTrue(!locations.isEmpty(), "there must be at least one location");
    }
  }
}
