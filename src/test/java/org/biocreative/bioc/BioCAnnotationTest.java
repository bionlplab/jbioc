package org.biocreative.bioc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BioCAnnotationTest {

  private static final String ID = "1";
  private static final String TEXT = "ABC";
  
  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";
  
  private static final BioCLocation LOC_1 = new BioCLocation(0, 1);
  private static final BioCLocation LOC_2 = new BioCLocation(1, 2);
  
  private static BioCAnnotation base;
  private static BioCAnnotation baseCopy;

  @Rule
  public ExpectedException thrown = ExpectedException.none();
  
  @Before
  public void setUp() {
    base =  new BioCAnnotation();
    base.setID(ID);
    base.addLocation(LOC_1);
    base.addLocation(LOC_2);
    base.setText(TEXT);
    base.putInfon(KEY, VALUE);
    
    System.out.println(base);
    
    baseCopy =  new BioCAnnotation();
    baseCopy.setID(ID);
    baseCopy.addLocation(LOC_1);
    baseCopy.addLocation(LOC_2);
    baseCopy.setText(TEXT);
    baseCopy.putInfon(KEY, VALUE);
  }

  @Test
  public void test_equals() {
    assertEquals(base, baseCopy);
  }
  
  @Test
  public void test_copy() {
    assertEquals(base, new BioCAnnotation(base));
  }

  @Test
  public void test_allFields() {
    assertEquals(base.getID(), ID);
    assertEquals(base.getText(), TEXT);
    assertEquals(base.getInfon(KEY), VALUE);
    assertEquals(base.getLocations().size(), 2);
    assertEquals(base.getLocations().get(0), LOC_1);
    assertEquals(base.getLocations().get(1), LOC_2);
  }
}
