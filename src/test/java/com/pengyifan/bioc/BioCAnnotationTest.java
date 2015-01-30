package com.pengyifan.bioc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.Lists;
import com.google.common.testing.EqualsTester;
import com.pengyifan.bioc.BioCAnnotation;
import com.pengyifan.bioc.BioCLocation;

public class BioCAnnotationTest {

  private static final String ID = "1";
  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";
  private static final String TEXT = "ABC";

  private static final String ID_2 = "2";
  private static final String KEY_2 = "KEY2";
  private static final String VALUE_2 = "VALUE2";
  private static final String TEXT_2 = "DEF";

  private static final BioCLocation LOC_1 = new BioCLocation(0, 1);
  private static final BioCLocation LOC_2 = new BioCLocation(1, 2);
  private static final BioCLocation LOC_3 = new BioCLocation(2, 3);

  private BioCAnnotation base;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    base = new BioCAnnotation();
    base.setID(ID);
    base.addLocation(LOC_1);
    base.addLocation(LOC_2);
    base.setText(TEXT);
    base.putInfon(KEY, VALUE);
  }

  @Test
  public void test_equals() {
    BioCAnnotation baseCopy = new BioCAnnotation(base);

    BioCAnnotation diffId = new BioCAnnotation(base);
    diffId.setID(ID_2);

    BioCAnnotation diffInfon = new BioCAnnotation(base);
    diffInfon.putInfon(KEY_2, VALUE_2);

    BioCAnnotation diffLocation = new BioCAnnotation(base);
    diffLocation.addLocation(LOC_3);
    
    BioCAnnotation diffText = new BioCAnnotation(base);
    diffText.setText(TEXT_2);

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffId)
        .addEqualityGroup(diffLocation)
        .addEqualityGroup(diffInfon)
        .addEqualityGroup(diffText)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    assertEquals(ID, base.getID());
    assertEquals(TEXT, base.getText().get());
    assertEquals(VALUE, base.getInfon(KEY).get());
    assertEquals(2, base.getLocationCount());
    assertTrue(base.containsLocation(LOC_1));
    assertTrue(base.containsLocation(LOC_2));
  }

  @Test
  public void test_nullID() {
    base.setID(null);
    thrown.expect(NullPointerException.class);
    base.getID();
  }

  @Test
  public void test_nullLocation() {
    thrown.expect(NullPointerException.class);
    base.addLocation(null);
  }

  @Test
  public void test_nullText() {
    base.setText(null);
    assertFalse(base.getText().isPresent());
  }

  @Test
  public void test_clearLocations() {
    base.clearLocations();
    assertTrue(base.getLocations().isEmpty());
  }

  @Test
  public void test_removeInfon() {
    base.removeInfon(KEY);
    assertFalse(base.getInfon(KEY).isPresent());
  }

  @Test
  public void test_clearInfons() {
    base.clearInfons();
    assertTrue(base.getInfons().isEmpty());
  }

  @Test
  public void test_addLocation() {
    assertFalse(base.containsLocation(LOC_3));
    base.addLocation(new BioCLocation(LOC_3.getOffset(), LOC_3.getLength()));
    assertTrue(base.containsLocation(LOC_3));
  }

  @Test
  public void test_getInfon_nullKey() {
    assertFalse(base.getInfon(null).isPresent());
  }

  @Test
  public void test_nodeIterator() {
    List<BioCLocation> expected = Lists.newArrayList(LOC_1, LOC_2);
    List<BioCLocation> actual = Lists.newArrayList(base.locationIterator());
    assertThat(actual, is(expected));
  }
}
