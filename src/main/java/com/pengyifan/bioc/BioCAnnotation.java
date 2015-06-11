package com.pengyifan.bioc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Stand off annotation.
 * 
 * @since 1.0.0
 * @author Yifan Peng
 */
public class BioCAnnotation {

  private String id;
  private Map<String, String> infons;
  private Set<BioCLocation> locations;
  private String text;

  /**
   * Constructs an empty annotation.
   */
  public BioCAnnotation() {
    infons = Maps.newHashMap();
    locations = Sets.newHashSet();
  }
  
  /**
   * Constructs an empty annotation with id.
   * 
   * @param id the id used to identify annotation
   */
  public BioCAnnotation(String id) {
    this.id = id;
    infons = Maps.newHashMap();
    locations = Sets.newHashSet();
  }

  /**
   * Constructs an annotation containing the information of the specified
   * annotation.
   * 
   * @param annotation the annotation whose information is to be placed into
   *          this annotation
   */
  public BioCAnnotation(BioCAnnotation annotation) {
    this();
    setID(annotation.id);
    setText(annotation.text);
    setInfons(annotation.infons);
    setLocations(annotation.locations);
  }

  /**
   * Adds the location at the specified position in this annotation.
   * 
   * @param location The location at the specified position in this annotation
   */
  public void addLocation(BioCLocation location) {
    checkNotNull(location, "location cannot be null");
    locations.add(location);
  }

  /**
   * Clears all information.
   */
  public void clearInfons() {
    infons.clear();
  }

  /**
   * Clears all locations in this annotation.
   */
  public void clearLocations() {
    locations.clear();
  }

  /**
   * Returns true if this annotation contains the specified location.
   * 
   * @param location location whose presence in this annotation is to be tested
   * @return if this annotation contains the specified location
   */
  public boolean containsLocation(BioCLocation location) {
    return locations.contains(location);
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
   * 
   * @return the id used to identify this annotation in a {@link BioCRelation}.
   */
  public String getID() {
    checkNotNull(id, "id has to be set");
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
   * Returns the information in this annotation.
   * 
   * @return the information in this annotation.
   */
  public Map<String, String> getInfons() {
    return infons;
  }

  /**
   * Returns the number of locations in this annotation.
   * 
   * @return the number of locations in this annotation
   */
  public int getLocationCount() {
    return locations.size();
  }

  /**
   * Returns locations of the annotated text. Multiple locations indicate a
   * multispan annotation.
   * 
   * @return locations of the annotated text
   */
  public Set<BioCLocation> getLocations() {
    return locations;
  }

  /**
   * Returns the minimal range which encloses all locations in this annotation.
   *
   * @return the minimal range which encloses all locations in this annotation
   */
  public BioCLocation getTotalLocation() {
    checkArgument(getLocationCount()>0, "No location added");
    RangeSet<Integer> rangeSet = TreeRangeSet.create();
    for(BioCLocation location: getLocations()) {
      rangeSet.add(
          Range.closedOpen(location.getOffset(), location.getOffset() + location.getLength()));
    }
    Range<Integer> totalSpan = rangeSet.span();
    return new BioCLocation(totalSpan.lowerEndpoint(),
        totalSpan.upperEndpoint() - totalSpan.lowerEndpoint());
  }

  /**
   * Returns the original text of the annotation
   * 
   * @return the original text of the annotation
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
   * 
   * @return an iterator over the locations in this annotation in proper
   *         sequence
   */
  public Iterator<BioCLocation> locationIterator() {
    return locations.iterator();
  }

  /**
   * Associates the specified value with the specified key in this annotation.
   * 
   * @param key key with which the specified value is to be associated
   * @param value value to be associated with the specified key
   */
  public void putInfon(String key, String value) {
    infons.put(key, value);
  }

  /**
   * Removes the value for a key from this annotation if it is present
   * (optional operation).
   * 
   * @param key key with which the specified value is to be associated
   */
  public void removeInfon(String key) {
    infons.remove(key);
  }

  /**
   * Sets the id used to identify this annotation in a {@link BioCRelation}.
   * 
   * @param id the id used to identify this annotation in a
   *          {@link BioCRelation}
   */
  public void setID(String id) {
    this.id = id;
  }

  /**
   * Sets the information in this annotation.
   * 
   * @param infons the information in this annotation
   */
  public void setInfons(Map<String, String> infons) {
    clearInfons();
    this.infons.putAll(infons);
  }

  /**
   * Sets the locations in this annotation.
   * 
   * @param locations the locations in this annotation.
   */
  public void setLocations(Set<BioCLocation> locations) {
    clearLocations();
    this.locations.addAll(locations);
  }

  /**
   * Sets the original text of the annotation.
   * 
   * @param text the original text
   */
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
