package com.pengyifan.bioc.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.lang3.Validate;

import com.pengyifan.bioc.BioCAnnotation;
import com.pengyifan.bioc.BioCCollection;
import com.pengyifan.bioc.BioCDocument;
import com.pengyifan.bioc.BioCLocation;
import com.pengyifan.bioc.BioCNode;
import com.pengyifan.bioc.BioCPassage;
import com.pengyifan.bioc.BioCRelation;
import com.pengyifan.bioc.BioCSentence;

/**
 * can only read collection DTD or sentence DTD
 */
class BioCReader implements Closeable {

  static enum Level {
    COLLECTION_LEVEL, DOCUMENT_LEVEL, PASSAGE_LEVEL, SENTENCE_LEVEL
  }

  BioCCollection collection;
  BioCDocument document;
  BioCPassage passage;
  BioCSentence sentence;
  XMLEventReader reader;
  String dtd;
  private int state;

  Level level;

  protected BioCReader(Reader reader, Level level)
      throws FactoryConfigurationError, XMLStreamException {
    XMLInputFactory factory = XMLInputFactory.newInstance();
    factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
    factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
    factory.setProperty(XMLInputFactory.IS_COALESCING, false);
    factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
    factory.setProperty(XMLInputFactory.SUPPORT_DTD, true);
    this.reader = factory.createXMLEventReader(reader);
    this.level = level;
    state = 0;
  }

  protected String getDtd() {
    return dtd;
  }

  @Override
  public void close()
      throws IOException {
    try {
      reader.close();
    } catch (XMLStreamException e) {
      throw new IOException(e.getMessage(), e);
    }
  }

  protected Object read()
      throws XMLStreamException {

    String localName = null;

    while (reader.hasNext()) {
      XMLEvent event = reader.nextEvent();
      switch (state) {
      case 0:
        if (event.isStartElement()) {
          StartElement startElement = event.asStartElement();
          localName = startElement.getName().getLocalPart();
          if (localName.equals("collection")) {
            collection = new BioCCollection();
            state = 1;
          }
        }
        if (event.getEventType() == XMLStreamConstants.DTD) {
          DTD dtd = (DTD) event;
          this.dtd = dtd.getDocumentTypeDeclaration();
        }
        break;
      case 1:
        if (event.isStartElement()) {
          StartElement startElement = event.asStartElement();
          localName = startElement.getName().getLocalPart();
          if (localName.equals("source")) {
            collection.setSource(getText());
          } else if (localName.equals("date")) {
            collection.setDate(getText());
          } else if (localName.equals("key")) {
            collection.setKey(getText());
          } else if (localName.equals("infon")) {
            collection.putInfon(
                getAttribute(startElement, "key"),
                getText());
          } else if (localName.equals("document")) {
            // read document
            document = new BioCDocument();
            state = 2;
          } else {
            ;
          }
        }
        else if (event.isEndElement()) {
          EndElement endElement = event.asEndElement();
          localName = endElement.getName().getLocalPart();
          if (localName.equals("collection")) {
            sentence = null;
            passage = null;
            document = null;
            state = 0;
          }
          break;
        }
        break;
      case 2:
        if (event.isStartElement()) {
          StartElement startElement = event.asStartElement();
          localName = startElement.getName().getLocalPart();
          if (localName.equals("id")) {
            document.setID(getText());
          } else if (localName.equals("infon")) {
            document.putInfon(
                getAttribute(startElement, "key"),
                getText());
          } else if (localName.equals("passage")) {
            // read passage
            passage = new BioCPassage();
            state = 3;
          } else if (localName.equals("relation")) {
            // read relation
            document.addRelation(readRelation(startElement));
          } else {
            ;
          }
        }
        else if (event.isEndElement()) {
          EndElement endElement = event.asEndElement();
          localName = endElement.getName().getLocalPart();
          if (localName.equals("document")) {
            state = 1;
            if (level == Level.DOCUMENT_LEVEL) {
              return document;
            } else if (document != null) {
              collection.addDocument(document);
            }
          }
          break;
        }
        break;
      case 3:
        if (event.isStartElement()) {
          StartElement startElement = event.asStartElement();
          localName = startElement.getName().getLocalPart();
          if (localName.equals("offset")) {
            passage.setOffset(Integer.parseInt(getText()));
          } else if (localName.equals("text")) {
            passage.setText(getText());
          } else if (localName.equals("infon")) {
            passage.putInfon(
                getAttribute(startElement, "key"),
                getText());
          } else if (localName.equals("annotation")) {
            passage.addAnnotation(readAnnotation(startElement));
          } else if (localName.equals("relation")) {
            passage.addRelation(readRelation(startElement));
          } else if (localName.equals("sentence")) {
            // read sentence
            sentence = new BioCSentence();
            state = 4;
          } else {
            ;
          }
        }
        else if (event.isEndElement()) {
          EndElement endElement = event.asEndElement();
          localName = endElement.getName().getLocalPart();
          if (localName.equals("passage")) {
            state = 2;
            if (level == Level.PASSAGE_LEVEL) {
              return passage;
            } else if (passage != null) {
              document.addPassage(passage);
            }
          }
          break;
        }
        break;
      case 4:
        if (event.isStartElement()) {
          StartElement startElement = event.asStartElement();
          localName = startElement.getName().getLocalPart();
          if (localName.equals("offset")) {
            sentence.setOffset(Integer.parseInt(getText()));
          } else if (localName.equals("text")) {
            sentence.setText(getText());
          } else if (localName.equals("infon")) {
            sentence.putInfon(
                getAttribute(startElement, "key"),
                getText());
          } else if (localName.equals("annotation")) {
            sentence.addAnnotation(readAnnotation(startElement));
          } else if (localName.equals("relation")) {
            sentence.addRelation(readRelation(startElement));
          } else {
            ;
          }
        }
        else if (event.isEndElement()) {
          EndElement endElement = event.asEndElement();
          localName = endElement.getName().getLocalPart();
          if (localName.equals("sentence")) {
            state = 3;
            if (level == Level.SENTENCE_LEVEL) {
              return sentence;
            } else if (sentence != null) {
              passage.addSentence(sentence);
            }
          }
          break;
        }
      }
    }
    return collection;
  }

  private String getText()
      throws XMLStreamException {
    return reader.nextEvent().asCharacters().getData();
  }

  private BioCAnnotation readAnnotation(StartElement annotationEvent)
      throws XMLStreamException {
    BioCAnnotation ann = new BioCAnnotation();
    ann.setID(getAttribute(annotationEvent, "id"));

    String localName = null;

    while (reader.hasNext()) {
      XMLEvent event = reader.nextEvent();
      if (event.isStartElement()) {
        StartElement startElement = event.asStartElement();
        localName = startElement.getName().getLocalPart();
        if (localName.equals("text")) {
          ann.setText(getText());
        } else if (localName.equals("infon")) {
          ann.putInfon(
              startElement.getAttributeByName(new QName("key")).getValue(),
              getText());
        } else if (localName.equals("location")) {
          ann.addLocation(new BioCLocation(
              Integer.parseInt(getAttribute(startElement, "offset")),
              Integer.parseInt(getAttribute(startElement, "length"))));
        }
      }
      else if (event.isEndElement()) {
        EndElement endElement = event.asEndElement();
        localName = endElement.getName().getLocalPart();
        if (localName.equals("annotation")) {
          return ann;
        }
      }
    }
    Validate.isTrue(false, "should not reach here");
    return null;
  }

  private BioCRelation readRelation(StartElement relationEvent)
      throws XMLStreamException {
    BioCRelation rel = new BioCRelation();
    rel.setID(getAttribute(relationEvent, "id"));

    String localName = null;

    while (reader.hasNext()) {
      XMLEvent event = reader.nextEvent();
      if (event.isStartElement()) {
        StartElement startElement = event.asStartElement();
        localName = startElement.getName().getLocalPart();
        if (localName.equals("infon")) {
          rel.putInfon(
              getAttribute(startElement, "key"),
              getText());
        } else if (localName.equals("node")) {
          BioCNode node = new BioCNode(getAttribute(startElement, "refid"),
              getAttribute(startElement, "role"));
          rel.addNode(node);
        }
      }
      else if (event.isEndElement()) {
        EndElement endElement = event.asEndElement();
        localName = endElement.getName().getLocalPart();
        if (localName.equals("relation")) {
          return rel;
        }
      }
    }
    Validate.isTrue(false, "should not reach here");
    return null;
  }

  private String getAttribute(StartElement startElement, String key) {
    return startElement.getAttributeByName(new QName(key)).getValue();
  }
}
