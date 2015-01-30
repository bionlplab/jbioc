package com.pengyifan.bioc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.Lists;
import com.google.common.testing.EqualsTester;

public class BioCCollectionTest {

  private static final String SOURCE = "nowhere";
  private static final String DATE = "today";
  private static final String KEY = "KEY";
  private static final String VALUE = "VALUE";
  private static final String VERSION = "v1";
  private static final String ENCODING = "e1";

  private static final String SOURCE_2 = "somewhere";
  private static final String DATE_2 = "tmw";
  private static final String KEY_2 = "KEY2";
  private static final String VALUE_2 = "VALUE2";
  private static final String VERSION_2 = "v2";
  private static final String ENCODING_2 = "e2";

  private static final BioCDocument DOC_1 = createDocument("1");
  private static final BioCDocument DOC_2 = createDocument("2");

  private BioCCollection base;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    base = new BioCCollection();
    base.setKey(KEY);
    base.setSource(SOURCE);
    base.setDate(DATE);
    base.putInfon(KEY, VALUE);
    base.setEncoding(ENCODING);
    base.setVersion(VERSION);
    base.setStandalone(true);
    base.addDocument(DOC_1);
  }

  @Test
  public void test_equals() {
    BioCCollection baseCopy = new BioCCollection(base);

    BioCCollection diffSource = new BioCCollection(base);
    diffSource.setSource(SOURCE_2);

    BioCCollection diffInfon = new BioCCollection(base);
    diffInfon.putInfon(KEY_2, VALUE_2);

    BioCCollection diffDate = new BioCCollection(base);
    diffDate.setDate(DATE_2);

    BioCCollection diffKey = new BioCCollection(base);
    diffKey.setKey(KEY_2);
    
    BioCCollection diffVersion = new BioCCollection(base);
    diffVersion.setVersion(VERSION_2);
    
    BioCCollection diffEncoding = new BioCCollection(base);
    diffEncoding.setEncoding(ENCODING_2);
    
    BioCCollection diffStandalone = new BioCCollection(base);
    diffStandalone.setStandalone(false);
    
    BioCCollection diffDoc = new BioCCollection(base);
    diffDoc.addDocument(DOC_2);

    new EqualsTester()
        .addEqualityGroup(base, baseCopy)
        .addEqualityGroup(diffSource)
        .addEqualityGroup(diffDate)
        .addEqualityGroup(diffKey)
        .addEqualityGroup(diffInfon)
        .addEqualityGroup(diffVersion)
        .addEqualityGroup(diffEncoding)
        .addEqualityGroup(diffStandalone)
        .addEqualityGroup(diffDoc)
        .testEquals();
  }

  @Test
  public void test_allFields() {
    assertEquals(base.getSource(), SOURCE);
    assertEquals(base.getDate(), DATE);
    assertEquals(base.getVersion(), VERSION);
    assertEquals(base.getEncoding(), ENCODING);
    assertEquals(base.isStandalone(), true);
    assertEquals(base.getInfon(KEY).get(), VALUE);
    assertEquals(base.getKey(), KEY);
    assertEquals(DOC_1, base.getDocument(0));
  }

  @Test
  public void test_nullSource() {
    base.setSource(null);
    thrown.expect(NullPointerException.class);
    base.getSource();
  }

  @Test
  public void test_nullDate() {
    base.setDate(null);
    thrown.expect(NullPointerException.class);
    base.getDate();
  }

  @Test
  public void test_nullKey() {
    base.setKey(null);
    thrown.expect(NullPointerException.class);
    base.getKey();
  }

  @Test
  public void test_getInfon_nullKey() {
    assertFalse(base.getInfon(null).isPresent());
  }

  @Test
  public void test_nullDocument() {
    thrown.expect(NullPointerException.class);
    base.addDocument(null);
  }

  @Test
  public void test_removeInfon() {
    base.removeInfon(KEY);
    assertFalse(base.getInfon(KEY).isPresent());
  }

  @Test
  public void test_addDocument() {
    base.addDocument(DOC_2);
    assertEquals(DOC_1, base.getDocument(0));
    assertEquals(DOC_2, base.getDocument(1));
  }
  
  @Test
  public void test_documentIterator() {
    base.addDocument(DOC_2);
    List<BioCDocument> expected = Lists.newArrayList(DOC_1, DOC_2);
    List<BioCDocument> actual = Lists.newArrayList(base.documentIterator());
    assertThat(actual, is(expected));
  }

  private static BioCDocument createDocument(String id) {
    BioCDocument doc = new BioCDocument();
    doc.setID(id);
    return doc;
  }
}
