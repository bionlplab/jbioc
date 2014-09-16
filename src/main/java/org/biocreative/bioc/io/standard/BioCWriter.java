package org.biocreative.bioc.io.standard;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import javanet.staxutils.IndentingXMLEventWriter;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import org.biocreative.bioc.BioCAnnotation;
import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.BioCLocation;
import org.biocreative.bioc.BioCNode;
import org.biocreative.bioc.BioCPassage;
import org.biocreative.bioc.BioCRelation;
import org.biocreative.bioc.BioCSentence;

class BioCWriter implements Closeable {

  XMLEventWriter writer;
  XMLEventFactory eventFactory = XMLEventFactory.newInstance();

  protected BioCWriter(Writer writer)
      throws FactoryConfigurationError, XMLStreamException {
    XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
    this.writer = new IndentingXMLEventWriter(
        xmlOutputFactory.createXMLEventWriter(writer));
  }

  protected BioCWriter writeStartDocument(String encoding,
      String version,
      boolean standalone)
      throws XMLStreamException {
    writer.add(eventFactory.createStartDocument(encoding, version, standalone));
    return this;
  }

  protected BioCWriter writeEndDocument()
      throws XMLStreamException {
    writer.add(eventFactory.createEndDocument());
    return this;
  }

  protected BioCWriter writeDTD(String dtd)
      throws XMLStreamException {
    writer.add(eventFactory.createDTD(dtd));
    return this;
  }

  protected BioCWriter write(BioCPassage passage)
      throws XMLStreamException {
    writeStartElement("passage")
        // infon
        .write(passage.getInfons())
        // offset
        .writeStartElement("offset")
        .writeCharacters(Integer.toString(passage.getOffset()))
        .writeEndElement("offset");
    // text
    if (passage.getText().isPresent()) {
      write(passage.getText().get());
    }
    // sen
    for (BioCSentence sen : passage.getSentences()) {
      write(sen);
    }
    // ann
    for (BioCAnnotation ann : passage.getAnnotations()) {
      write(ann);
    }
    // rel
    for (BioCRelation rel : passage.getRelations()) {
      write(rel);
    }

    writeEndElement("passage");
    return this;
  }

  protected BioCWriter write(BioCSentence sentence)
      throws XMLStreamException {
    writeStartElement("sentence")
        // infon
        .write(sentence.getInfons())
        // offset
        .writeStartElement("offset")
        .writeCharacters(Integer.toString(sentence.getOffset()))
        .writeEndElement("offset");

    // text
    if (sentence.getText().isPresent()) {
      write(sentence.getText().get());
    }
    // ann
    for (BioCAnnotation ann : sentence.getAnnotations()) {
      write(ann);
    }
    // rel
    for (BioCRelation rel : sentence.getRelations()) {
      write(rel);
    }
    writeEndElement("sentence");
    return this;
  }

  protected final BioCWriter write(BioCAnnotation annotation)
      throws XMLStreamException {
    writeStartElement("annotation")
        // id
        .writeAttribute("id", annotation.getID())
        // infon
        .write(annotation.getInfons());
    // location
    for (BioCLocation loc : annotation.getLocations()) {
      write(loc);
    }
    // text
    if (annotation.getText().isPresent()) {
      write(annotation.getText().get());
    }
    //
    writeEndElement("annotation");
    return this;
  }

  protected final BioCWriter write(BioCLocation location)
      throws XMLStreamException {
    return writeStartElement("location")
        .writeAttribute("offset", location.getOffset())
        .writeAttribute("length", location.getLength())
        .writeEndElement("location");
  }

  protected final BioCWriter write(String text)
      throws XMLStreamException {
    return writeStartElement("text")
        .writeCharacters(text)
        .writeEndElement("text");
  }

  protected final BioCWriter write(BioCRelation relation)
      throws XMLStreamException {
    writeStartElement("relation")
        // id
        .writeAttribute("id", relation.getID())
        // infon
        .write(relation.getInfons());
    // labels
    for (BioCNode label : relation.getNodes()) {
      write(label);
    }
    writeEndElement("relation");
    return this;
  }

  protected final BioCWriter write(BioCNode node)
      throws XMLStreamException {
    return writeStartElement("node")
        .writeAttribute("refid", node.getRefid())
        .writeAttribute("role", node.getRole())
        .writeEndElement("node");
  }

  protected final BioCWriter write(BioCDocument document)
      throws XMLStreamException {
    writeStartElement("document")
        // id
        .writeStartElement("id")
        .writeCharacters(document.getID())
        .writeEndElement("id")
        // infon
        .write(document.getInfons());
    // passages
    for (BioCPassage passage : document.getPassages()) {
      write(passage);
    }
    // relations
    for (BioCRelation rel : document.getRelations()) {
      write(rel);
    }
    writeEndElement("document");
    return this;
  }

  protected final BioCWriter write(Map<String, String> infons)
      throws XMLStreamException {
    for (Entry<String, String> infon : infons.entrySet()) {
      writeStartElement("infon")
          .writeAttribute("key", infon.getKey())
          .writeCharacters(infon.getValue())
          .writeEndElement("infon");
    }
    return this;
  }

  protected final BioCWriter writeEndCollection()
      throws XMLStreamException {
    return writeEndElement("collection");
  }

  protected final BioCWriter
      writeBeginCollectionInfo(BioCCollection collection)
          throws XMLStreamException {
    return writeStartElement("collection")
        // source
        .writeStartElement("source")
        .writeCharacters(collection.getSource())
        .writeEndElement("source")
        // date
        .writeStartElement("date")
        .writeCharacters(collection.getDate())
        .writeEndElement("date")
        // key
        .writeStartElement("key")
        .writeCharacters(collection.getKey())
        .writeEndElement("key")
        // info
        .write(collection.getInfons());
  }

  @Override
  public void close()
      throws IOException {
    try {
      writer.flush();
      writer.close();
    } catch (XMLStreamException e) {
      throw new IOException(e.getMessage());
    }
  }

  private QName getElement(String localPart) {
    return new QName(localPart);
  }

  private BioCWriter writeStartElement(String localPart)
      throws XMLStreamException {
    writer.add(eventFactory.createStartElement(
        getElement(localPart),
        null,
        null));
    return this;
  }

  private BioCWriter writeEndElement(String localPart)
      throws XMLStreamException {
    writer.add(eventFactory.createEndElement(getElement(localPart), null));
    return this;
  }

  private BioCWriter writeCharacters(String text)
      throws XMLStreamException {
    writer.add(eventFactory.createCharacters(text));
    return this;
  }

  private BioCWriter writeAttribute(String key, String value)
      throws XMLStreamException {
    writer.add(eventFactory.createAttribute(key, value));
    return this;
  }

  private BioCWriter writeAttribute(String key, int value)
      throws XMLStreamException {
    return writeAttribute(key, Integer.toString(value));
  }
}
