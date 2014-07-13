package org.biocreative.bioc.io.standard;

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
import org.biocreative.bioc.BioCAnnotation;
import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.BioCLocation;
import org.biocreative.bioc.BioCNode;
import org.biocreative.bioc.BioCPassage;
import org.biocreative.bioc.BioCRelation;
import org.biocreative.bioc.BioCSentence;

/**
 * can only read collection DTD or sentence DTD
 */
class BioCReader2 implements Closeable {

  static enum Level {
    COLLECTION_LEVEL, DOCUMENT_LEVEL, PASSAGE_LEVEL, SENTENCE_LEVEL
  }

  BioCCollection.Builder collectionBuilder;
  BioCDocument.Builder documentBuilder;
  BioCPassage.Builder passageBuilder;
  BioCSentence.Builder sentenceBuilder;
  XMLEventReader reader;
  String dtd;
  private int state;

  Level level;

  public BioCReader2(Reader reader, Level level)
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
            collectionBuilder = BioCCollection.newBuilder();
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
            collectionBuilder.setSource(readText());
          } else if (localName.equals("date")) {
            collectionBuilder.setDate(readText());
          } else if (localName.equals("key")) {
            collectionBuilder.setKey(readText());
          } else if (localName.equals("infon")) {
            collectionBuilder.putInfon(
                startElement.getAttributeByName(new QName("key")).getValue(),
                readText());
          } else if (localName.equals("document")) {
            // read document
            documentBuilder = BioCDocument.newBuilder();
            state = 2;
          } else {
            ;
          }
        }
        else if (event.isEndElement()) {
          EndElement endElement = event.asEndElement();
          localName = endElement.getName().getLocalPart();
          if (localName.equals("collection")) {
            sentenceBuilder = null;
            passageBuilder = null;
            documentBuilder = null;
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
            documentBuilder.setID(readText());
          } else if (localName.equals("infon")) {
            documentBuilder.putInfon(
                startElement.getAttributeByName(new QName("key")).getValue(),
                readText());
          } else if (localName.equals("passage")) {
            // read passage
            passageBuilder = BioCPassage.newBuilder();
            state = 3;
          } else if (localName.equals("relation")) {
            // read relation
            documentBuilder.addRelation(readRelation(startElement));
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
              return documentBuilder.build();
            } else if (documentBuilder != null) {
              collectionBuilder.addDocument(documentBuilder.build());
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
            passageBuilder.setOffset(Integer.parseInt(readText()));
          } else if (localName.equals("text")) {
            passageBuilder.setText(readText());
          } else if (localName.equals("infon")) {
            passageBuilder.putInfon(
                startElement.getAttributeByName(new QName("key")).getValue(),
                readText());
          } else if (localName.equals("annotation")) {
            passageBuilder.addAnnotation(readAnnotation(startElement));
          } else if (localName.equals("relation")) {
            passageBuilder.addRelation(readRelation(startElement));
          } else if (localName.equals("sentence")) {
            // read sentence
            sentenceBuilder = BioCSentence.newBuilder();
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
              return passageBuilder.build();
            } else if (passageBuilder != null) {
              documentBuilder.addPassage(passageBuilder.build());
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
            sentenceBuilder.setOffset(Integer.parseInt(readText()));
          } else if (localName.equals("text")) {
            sentenceBuilder.setText(readText());
          } else if (localName.equals("infon")) {
            sentenceBuilder.putInfon(
                startElement.getAttributeByName(new QName("key")).getValue(),
                readText());
          } else if (localName.equals("annotation")) {
            sentenceBuilder.addAnnotation(readAnnotation(startElement));
          } else if (localName.equals("relation")) {
            sentenceBuilder.addRelation(readRelation(startElement));
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
              return sentenceBuilder.build();
            } else if (sentenceBuilder != null) {
              passageBuilder.addSentence(sentenceBuilder.build());
            }
          }
          break;
        }
      }
    }
    return collectionBuilder.build();
  }

  private String readText()
      throws XMLStreamException {
    return reader.nextEvent().asCharacters().getData();
  }

  private BioCAnnotation readAnnotation(StartElement annotationEvent)
      throws XMLStreamException {
    BioCAnnotation.Builder annBuilder = BioCAnnotation.newBuilder();
    annBuilder.setID(annotationEvent.getAttributeByName(new QName("id"))
        .getValue());

    String localName = null;

    while (reader.hasNext()) {
      XMLEvent event = reader.nextEvent();
      if (event.isStartElement()) {
        StartElement startElement = event.asStartElement();
        localName = startElement.getName().getLocalPart();
        if (localName.equals("text")) {
          annBuilder.setText(readText());
        } else if (localName.equals("infon")) {
          annBuilder.putInfon(
              startElement.getAttributeByName(new QName("key")).getValue(),
              readText());
        } else if (localName.equals("location")) {
          annBuilder.addLocation(BioCLocation
              .newBuilder()
              .setOffset(
                  Integer.parseInt(startElement.getAttributeByName(
                      new QName("offset")).getValue()))
              .setLength(
                  Integer.parseInt(startElement.getAttributeByName(
                      new QName("length")).getValue()))
              .build());
        }
      }
      else if (event.isEndElement()) {
        EndElement endElement = event.asEndElement();
        localName = endElement.getName().getLocalPart();
        if (localName.equals("annotation")) {
          return annBuilder.build();
        }
      }
    }
    Validate.isTrue(false, "should not reach here");
    return null;
  }

  private BioCRelation readRelation(StartElement relationEvent)
      throws XMLStreamException {
    BioCRelation.Builder relBuilder = BioCRelation.newBuilder();
    relBuilder.setID(relationEvent.getAttributeByName(new QName("id"))
        .getValue());

    String localName = null;

    while (reader.hasNext()) {
      XMLEvent event = reader.nextEvent();
      if (event.isStartElement()) {
        StartElement startElement = event.asStartElement();
        localName = startElement.getName().getLocalPart();
        if (localName.equals("infon")) {
          relBuilder.putInfon(
              startElement.getAttributeByName(new QName("key")).getValue(),
              readText());
        } else if (localName.equals("node")) {
          BioCNode node = BioCNode
              .newBuilder()
              .setRefid(
                  startElement.getAttributeByName(new QName("refid"))
                      .getValue())
              .setRole(
                  startElement.getAttributeByName(new QName("role")).getValue())
              .build();
          relBuilder.addNode(node);
        }
      }
      else if (event.isEndElement()) {
        EndElement endElement = event.asEndElement();
        localName = endElement.getName().getLocalPart();
        if (localName.equals("relation")) {
          return relBuilder.build();
        }
      }
    }
    Validate.isTrue(false, "should not reach here");
    return null;
  }
}
