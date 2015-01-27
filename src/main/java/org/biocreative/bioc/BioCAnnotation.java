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
 * Stand off annotation. The connection to the original text can be made
 * through the {@code location} and the {@code text} fields.
 */
public class BioCAnnotation {

  private String id;
  private Map<String, String> infons;
  private List<BioCLocation> locations;
  private String text;

  public BioCAnnotation() {
    infons = Maps.newHashMap();
    locations = Lists.newArrayList();
  }

  public BioCAnnotation(BioCAnnotation annotation) {
    this();
    this.id = annotation.id;
    this.text = annotation.text;
    this.infons.putAll(annotation.infons);
    this.locations.addAll(annotation.locations);
  }

  public void addLocation(BioCLocation location) {
    checkNotNull(location, "location cannot be null");
    locations.add(location);
  }

  public void addLocation(int offset, int length) {
    addLocation(new BioCLocation(offset, length));
  }

  public void clearInfons() {
    infons.clear();
  }

  public void clearLocations() {
    locations.clear();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BioCAnnotation)) {
      return false;
    }
    BioCAnnotation rhs = (BioCAnnotation) obj;
    return Objects.equals(id, rhs.id)
        && Objects.equals(text, rhs.text)
        && Objects.equals(infons, rhs.infons)
        && Objects.equals(locations, rhs.locations);
  }

  /**
   * Returns the id used to identify this annotation in a {@link BioCRelation}.
   */
  public String getID() {
    checkNotNull(id, "id has to be set");
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
   * Returns the information in this annotation.
   */
  public Map<String, String> getInfons() {
    return infons;
  }

  /**
   * Returns the location at the specified position in this annotation.
   */
  public BioCLocation getLocation(int i) {
    return locations.get(i);
  }

  /**
   * Returns the number of locations in this annotation.
   */
  public int getLocationCount() {
    return locations.size();
  }

  /**
   * Returns locations of the annotated text. Multiple locations indicate a
   * multispan annotation.
   */
  public List<BioCLocation> getLocations() {
    return locations;
  }

  /**
   * The original text of the annotation
   */
  public Optional<String> getText() {
    return Optional.ofNullable(text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, infons, locations);
  }

  /**
   * Returns a unmodifiable iterator over the locations in this annotation in
   * proper sequence.
   */
  public Iterator<BioCLocation> locationIterator() {
    return locations.iterator();
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
    this.infons = Maps.newHashMap(infons);
  }

  public void setLocations(List<BioCLocation> locations) {
    this.locations = Lists.newArrayList(locations);
  }

  public void setText(String text) {
    this.text = text;
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
}
