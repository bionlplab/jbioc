package com.pengyifan.bioc;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeRangeSet;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Stand off annotation.
 *
 * @author Yifan Peng
 * @since 1.0.0
 */
public class BioCAnnotation implements HasInfons, HasText, HasID, BioCObject {

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
   * Constructs an annotation containing the information of the specified annotation.
   *
   * @param annotation the annotation whose information is to be placed into this annotation
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

  @Override
  public String getID() {
    checkNotNull(id, "id has to be set");
    return id;
  }

  @Override
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
   * Returns locations of the annotated text. Multiple locations indicate a multi-span annotation.
   *
   * @return locations of the annotated text
   */
  public Set<BioCLocation> getLocations() {
    return locations;
  }

  @Override
  public Optional<String> getText() {
    return Optional.ofNullable(text);
  }

  /**
   * Returns the minimal range which encloses all locations in this annotation.
   *
   * @return the minimal range which encloses all locations in this annotation
   */
  public BioCLocation getTotalLocation() {
    checkArgument(getLocationCount() > 0, "No location added");
    RangeSet<Integer> rangeSet = TreeRangeSet.create();
    for (BioCLocation location : getLocations()) {
      rangeSet.add(
          Range.closedOpen(location.getOffset(), location.getOffset() + location.getLength()));
    }
    Range<Integer> totalSpan = rangeSet.span();
    return new BioCLocation(totalSpan.lowerEndpoint(),
        totalSpan.upperEndpoint() - totalSpan.lowerEndpoint());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, infons, locations);
  }

  /**
   * Returns a unmodifiable iterator over the locations in this annotation in proper sequence.
   *
   * @return an iterator over the locations in this annotation in proper sequence
   */
  public Iterator<BioCLocation> locationIterator() {
    return locations.iterator();
  }

  @Override
  public void setID(String id) {
    this.id = id;
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

  @Override
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
