package com.pengyifan.bioc.util;

import com.pengyifan.bioc.BioCAnnotation;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCLocation;
import com.pengyifan.bioc.BioCNode;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.BioCRelation;
import com.pengyifan.bioc.BioCSentence;
import com.pengyifan.bioc.io.BioCCollectionReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import javax.xml.stream.XMLStreamException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class BioCValidate3Test {

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Rule
  public final TemporaryFolder folder = new TemporaryFolder();

  private BioCValidate3 v = new BioCValidate3(false);
  private BioCDocument d = new BioCDocument();
  private BioCPassage p1 = new BioCPassage();
  private BioCPassage p2 = new BioCPassage();
  private BioCAnnotation a1 = new BioCAnnotation();
  private BioCAnnotation a2 = new BioCAnnotation();

  @Before
  public void before() throws IOException {
    v = new BioCValidate3(false);
    v.setPrintStream(new PrintStream(folder.newFile()));

    d.setID("foo");

    p1 = new BioCPassage();
    p1.setOffset(0);
    p1.setText("0123456789");
    d.addPassage(p1);

    p2 = new BioCPassage();
    p2.setOffset(11);
    p2.setText("0123456789");
    d.addPassage(p2);

    a1.setID("T1");
    a1.addLocation(new BioCLocation(11, 5));
    a1.setText("01234");
    p2.addAnnotation(a1);

    a2.setID("T2");
    a2.addLocation(new BioCLocation(13, 5));
    a2.setText("23456");
    p2.addAnnotation(a2);
  }

  @Test
  public void testEmpty () {
    v.check(new BioCCollection());
    v.check(new BioCDocument("foo"));
  }

  @Test
  public void testOverlappedPassages() {
    BioCDocument d = new BioCDocument();
    d.setID("foo");

    BioCPassage p = new BioCPassage();
    p.setOffset(0);
    p.setText("0123456789");
    d.addPassage(p);

    p = new BioCPassage();
    p.setOffset(9);
    p.setText("0123456789");
    d.addPassage(p);

    exception.expect(IllegalArgumentException.class);
    v.checkText(d);
  }

  @Test
  public void testOverlappedSentences() {
    BioCPassage p = new BioCPassage();
    p.setOffset(0);

    BioCSentence s = new BioCSentence();
    s.setOffset(0);
    s.setText("0123456789");
    p.addSentence(s);

    s = new BioCSentence();
    s.setOffset(9);
    s.setText("0123456789");
    p.addSentence(s);

    exception.expect(IllegalArgumentException.class);
    v.checkText(p);
  }

  @Test
  public void testAnnotation1() {
    v.check(d);

    BioCAnnotation a = new BioCAnnotation();
    a.setID("T1");
    a.addLocation(new BioCLocation(0, 2));
    a.setText("01");
    d.addAnnotation(a);
    v.check(d);

    a.setText("012");
    v.check(d);

    v.setThrowException(true);
    exception.expect(IllegalArgumentException.class);
    v.check(d);
  }

  @Test
  public void testAnnotation2() {
    v.check(d);

    BioCAnnotation a = new BioCAnnotation();
    a.setID("T1");
    a.addLocation(new BioCLocation(0, 5));
    a.setText("012");
    d.addAnnotation(a);
    v.check(d);

    v.setThrowException(true);
    exception.expect(IllegalArgumentException.class);
    v.check(d);
  }

  @Test
  public void testAnnotation3() {
    v.check(d);

    BioCAnnotation a = new BioCAnnotation();
    a.setID("T1");
    a.addLocation(new BioCLocation(0, 2)); // 01
    a.addLocation(new BioCLocation(1, 4)); // 1234
    a.setText("01234");
    d.addAnnotation(a);
    v.check(d);

    a.setText("012345");
    v.check(d);

    v.setThrowException(true);
    exception.expect(IllegalArgumentException.class);
    v.check(d);
  }

  @Test
  public void testRelation1() {
    v.check(d);

    BioCRelation r = new BioCRelation();
    r.setID("R1");
    r.addNode(new BioCNode(a1.getID(), "x1"));
    r.addNode(new BioCNode(a2.getID(), "x2"));
    p2.addRelation(r);
    v.check(d);

    r.getNode("x1").get().setRefid("T3");
    v.check(d);

    v.setThrowException(true);
    exception.expect(IllegalArgumentException.class);
    v.check(d);
  }

  @Test
  public void testRelation2() {
    v.check(d);

    BioCRelation r = new BioCRelation();
    r.setID("R1");
    r.addNode(new BioCNode(a1.getID(), "x1"));
    r.addNode(new BioCNode(a2.getID(), "x2"));
    d.addRelation(r);
    v.check(d);

    v.setThrowException(true);
    exception.expect(IllegalArgumentException.class);
    v.check(d);
  }
}