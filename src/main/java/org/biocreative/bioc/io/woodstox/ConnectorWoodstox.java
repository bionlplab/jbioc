package org.biocreative.bioc.io.woodstox;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;
import org.codehaus.stax2.XMLStreamWriter2;
import org.biocreative.bioc.BioCAnnotation;
import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.BioCLocation;
import org.biocreative.bioc.BioCNode;
import org.biocreative.bioc.BioCPassage;
import org.biocreative.bioc.BioCRelation;
import org.biocreative.bioc.BioCSentence;

;

/**
 * Read and write org.biocreative.bioc data using the woodstox StAX XML parser. BioCDocument at
 * a time IO avoids using excessive memory.
 */
public class ConnectorWoodstox implements Iterator<BioCDocument> {

  boolean          inDocument;
  boolean          finishedXML;
  XMLStreamReader2 xmlr;

  XMLStreamWriter2 xtw = null;

  /**
   * Call after last document has been written. Performs any needed cleanup and
   * closes the XML file.
   * @throws XMLStreamException 
   */
  public void endWrite() throws XMLStreamException {
      xtw.writeEndElement();
      xtw.writeEndDocument();
      xtw.flush();
      xtw.close();
  }

  void fromXML(BioCDocument.Builder documentBuilder)
      throws XMLStreamException {
    while (xmlr.hasNext()) {
      int eventType = xmlr.next();
      switch (eventType) {
      case XMLEvent.START_ELEMENT:
        String name = xmlr.getName().toString();
        if (name.equals("id")) {
          documentBuilder.setID(getString("id"));
        } else if (name.equals("infon")) {
            documentBuilder.putInfon(
                    xmlr.getAttributeValue("", "key"),
                    getString("infon"));
        } else if (name.equals("passage")) {
          documentBuilder.addPassage(getBioCPassage());
        } else if (name.equals("relation")) {
            documentBuilder.addRelation(getBioCRelation());
        }
        break;
      case XMLEvent.END_ELEMENT:
        if (xmlr.getName().toString().equals("document")) {
          inDocument = false;
        }
        return;
      }
    }
  }

  BioCAnnotation getBioCAnnotation()
      throws XMLStreamException {

    BioCAnnotation.Builder annotationBuilder = BioCAnnotation.newBuilder();

    String id = xmlr.getAttributeValue(null, "id");
    if (id != null) {
      annotationBuilder.setID(id);
    }

    while (xmlr.hasNext()) {
      int eventType = xmlr.next();
      switch (eventType) {
      case XMLEvent.START_ELEMENT:
        String name = xmlr.getName().toString();
        if (name.equals("infon")) {
          annotationBuilder.putInfon(
              xmlr.getAttributeValue("", "key"),
              getString("infon"));
        } else if (name.equals("location")) {
          annotationBuilder.addLocation(getBioCLocation());
        } else if (name.equals("text")) {
          annotationBuilder.setText(getString("text"));
        }
        break;
      case XMLEvent.END_ELEMENT:
        if (xmlr.getName().toString().equals("annotation")) {
          return annotationBuilder.build();
        } else if (xmlr.getName().toString().equals("infon")) {
          ;
        }
        throw new XMLStreamException("found at end of annotation: "
            + xmlr.getName().toString());
      }
    }
    return annotationBuilder.build();
  }

  BioCLocation getBioCLocation()
      throws XMLStreamException {
    BioCLocation location = BioCLocation
        .newBuilder()
        .setOffset(
            Integer.parseInt(xmlr.getAttributeValue(null, "offset")))
        .setLength(
            Integer.parseInt(xmlr.getAttributeValue(null, "length")))
        .build();
    xmlr.hasNext();
    xmlr.next();
    if (!xmlr.getName().toString().equals("location")) {
      throw new XMLStreamException("found at end of location: "
          + xmlr.getName().toString());
    }
    return location;
  }

  BioCNode getBioCNode()
      throws XMLStreamException {
    BioCNode node = BioCNode.newBuilder()
        .setRefid(xmlr.getAttributeValue(null, "refid"))
        .setRole(xmlr.getAttributeValue(null, "role"))
        .build();
    xmlr.hasNext();
    xmlr.next();
    if (!xmlr.getName().toString().equals("node")) {
      throw new XMLStreamException("found at end of node: "
          + xmlr.getName().toString());
    }
    return node;
  }

  BioCPassage getBioCPassage()
      throws XMLStreamException {

    BioCPassage.Builder passageBuilder =  BioCPassage.newBuilder();
    while (xmlr.hasNext()) {
      int eventType = xmlr.next();
      switch (eventType) {
      case XMLEvent.START_ELEMENT:
        String name = xmlr.getName().toString();
        if (name.equals("infon")) {
          passageBuilder.putInfon(
              xmlr.getAttributeValue("", "key"),
              getString("infon"));
        } else if (name.equals("offset")) {
          passageBuilder.setOffset(getInt("offset"));
        } else if (name.equals("text")) {
          passageBuilder.setText(getString("text"));
        } else if (name.equals("sentence")) {
          passageBuilder.addSentence(getBioCSentence());
        } else if (name.equals("annotation")) {
          passageBuilder.addAnnotation(getBioCAnnotation());
        } else if (name.equals("relation")) {
          passageBuilder.addRelation(getBioCRelation());
        }
        break;
      case XMLEvent.END_ELEMENT:
        if (xmlr.getName().toString().equals("passage")) {
          return passageBuilder.build();
        } else if (xmlr.getName().toString().equals("infon")) {
          ;
        }
        throw new XMLStreamException("found at end of passage: "
            + xmlr.getName().toString());
      }
    }
    return passageBuilder.build();
  }

  BioCRelation getBioCRelation()
      throws XMLStreamException {

    BioCRelation.Builder relationBuilder = BioCRelation.newBuilder();

    String id = xmlr.getAttributeValue(null, "id");
    if (id != null) {
      relationBuilder.setID(id);
    }

    while (xmlr.hasNext()) {
      int eventType = xmlr.next();
      switch (eventType) {
      case XMLEvent.START_ELEMENT:
        String name = xmlr.getName().toString();
        if (name.equals("infon")) {
          relationBuilder.putInfon(
              xmlr.getAttributeValue("", "key"),
              getString("infon"));
        } else if (name.equals("node")) {
          relationBuilder.addNode(getBioCNode());
        }
        break;
      case XMLEvent.END_ELEMENT:
        if (xmlr.getName().toString().equals("relation")) {
          return relationBuilder.build();
        } else if (xmlr.getName().toString().equals("infon")) {
          ;
        }
        throw new XMLStreamException("found at end of relation: "
            + xmlr.getName().toString());
      }
    }
    return relationBuilder.build();
  }

  BioCSentence getBioCSentence()
      throws XMLStreamException {

    BioCSentence.Builder sentenceBuilder = BioCSentence.newBuilder();

    while (xmlr.hasNext()) {
      int eventType = xmlr.next();
      switch (eventType) {
      case XMLEvent.START_ELEMENT:
        String name = xmlr.getName().toString();
        if (name.equals("infon")) {
          sentenceBuilder.putInfon(
              xmlr.getAttributeValue("", "key"),
              getString("infon"));
        } else if (name.equals("offset")) {
          sentenceBuilder.setOffset(getInt("offset"));
        } else if (name.equals("text")) {
          sentenceBuilder.setText(getString("text"));
        } else if (name.equals("annotation")) {
          sentenceBuilder.addAnnotation(getBioCAnnotation());
        } else if (name.equals("relation")) {
          sentenceBuilder.addRelation(getBioCRelation());
        }
        break;
      case XMLEvent.END_ELEMENT:
        if (xmlr.getName().toString().equals("sentence")) {
          return sentenceBuilder.build();
        } else if (xmlr.getName().toString().equals("infon")) {
          ;
        }
        throw new XMLStreamException("found at end of sentence: "
            + xmlr.getName().toString());
      }
    }
    return sentenceBuilder.build();
  }

  int getInt(String name)
      throws XMLStreamException {
    String strValue = getString(name);
    return Integer.parseInt(strValue);
  }

  String getString(String name)
      throws XMLStreamException {

    StringBuilder buf = new StringBuilder();
    while (xmlr.hasNext()) {
      int eventType = xmlr.next();
      switch (eventType) {
      case XMLEvent.CHARACTERS:
        buf.append(xmlr.getText());
        break;
      case XMLEvent.END_ELEMENT:
        if (xmlr.getName().toString().equals(name)) {
          return buf.toString();
        }
      }
    }

    return ""; // should NOT be here
  }

  /**
   * Returns true if the collection has more documents.
   */
  @Override
  public boolean hasNext() {
    if (finishedXML) {
      return false;
    }
    if (inDocument) {
      return true;
    }

    try {
      while (xmlr.hasNext()) {
        int eventType = xmlr.next();
        switch (eventType) {
        case XMLEvent.START_ELEMENT:
          if (xmlr.getName().toString().equals("document")) {
            inDocument = true;
            return true;
          }
          break;
        case XMLEvent.END_DOCUMENT:
          finishedXML = true;
          inDocument = false;
          return false;
        }
      }
    } catch (XMLStreamException ex) {
      /* This loses the exception, but hasNext is not allowed to throw
       * an exception. Could store the exception so it could be retrieved
       * later, but calling code would never know to retrieve the 
       * exception.
       */
      inDocument = false;
      return false;
    }

    // end of XML
    finishedXML = true;
    inDocument = false;
    return false;
  }

  /**
   * Returns the document in the collection.
   */
  @Override
  public BioCDocument next() {
    BioCDocument.Builder documentBuilder = BioCDocument.newBuilder();
    try {
      if (finishedXML) {
        return null;
      }
      if (!inDocument) {
        if (!hasNext()) {
          return null;
        }
        if (!inDocument) {
          throw new XMLStreamException(
              "*** impossible after hasNext() true; inDocument false");
        }
      }

      fromXML(documentBuilder);
    } catch (XMLStreamException ex) {
      throw new NoSuchElementException( ex.getMessage() );
    }
    return documentBuilder.build();
  }

  public BioCCollection parseXMLCollection(Reader xmlReader) 
      throws XMLStreamException {
    XMLInputFactory2 xmlif = null;
    xmlif = (XMLInputFactory2) XMLInputFactory2.newInstance();
    xmlif.setProperty(
        XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,
        Boolean.FALSE);
    xmlif.setProperty(
        XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,
        Boolean.FALSE);
    xmlif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
    xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
    xmlif.configureForSpeed();
    BioCCollection.Builder collectionBuilder =  BioCCollection.newBuilder();

    xmlr = (XMLStreamReader2) xmlif.createXMLStreamReader(xmlReader);
    int eventType = xmlr.getEventType();
    String curElement = "";
    finishedXML = false;
    inDocument = false;

    while (xmlr.hasNext() && !inDocument) {
      eventType = xmlr.next();
      switch (eventType) {
      case XMLEvent.START_ELEMENT:
        curElement = xmlr.getName().toString();
        if (curElement.equals("document")) {
          inDocument = true;
        } else if (curElement.equals("source")) {
          collectionBuilder.setSource(getString("source"));
        } else if (curElement.equals("date")) {
          collectionBuilder.setDate(getString("date"));
        } else if (curElement.equals("key")) {
          collectionBuilder.setKey(getString("key"));
        } else if (curElement.equals("infon")) {
          collectionBuilder.putInfon(
              xmlr.getAttributeValue("", "key"),
              getString("infon"));
        }
        break;
      case XMLEvent.END_ELEMENT:
        if (curElement.equals("collection")) {
          inDocument = false;
          finishedXML = true;
        }
        break;
      case XMLEvent.END_DOCUMENT:
        inDocument = false;
        finishedXML = true;
        break;
      }
    }
    while (hasNext()) {
      BioCDocument document = next();
      collectionBuilder.addDocument(document);
    }
    return collectionBuilder.build();
  }

  public BioCCollection parseXMLCollection(String xml)
      throws Exception {
    return parseXMLCollection(new StringReader(xml));
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

  /**
   * Start reading XML file
   * 
   * @param in Reader with XML to read
   * @throws XMLStreamException 
   */
  public BioCCollection startRead(Reader in) 
      throws XMLStreamException {
    XMLInputFactory2 xmlif = null;
    xmlif = (XMLInputFactory2) XMLInputFactory2.newInstance();
    xmlif.setProperty(
        XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,
        Boolean.FALSE);
    xmlif.setProperty(
        XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,
        Boolean.FALSE);
    //      xmlif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
    xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
    xmlif.configureForSpeed();

    BioCCollection.Builder collectionBuilder =  BioCCollection.newBuilder();

    xmlr = (XMLStreamReader2) xmlif.createXMLStreamReader(in);
    int eventType = xmlr.getEventType();
    String curElement = "";
    finishedXML = false;
    inDocument = false;

    while (xmlr.hasNext() && !inDocument) {
      eventType = xmlr.next();
      switch (eventType) {
      case XMLEvent.START_ELEMENT:
        curElement = xmlr.getName().toString();
        if (curElement.equals("document")) {
          inDocument = true;
        } else if (curElement.equals("source")) {
          collectionBuilder.setSource(getString("source"));
        } else if (curElement.equals("date")) {
          collectionBuilder.setDate(getString("date"));
        } else if (curElement.equals("key")) {
          collectionBuilder.setKey(getString("key"));
        } else if (curElement.equals("infon")) {
          collectionBuilder.putInfon(
              xmlr.getAttributeValue("", "key"),
              getString("infon"));
        }
        break;
      case XMLEvent.END_ELEMENT:
        if (curElement.equals("collection")) {
          inDocument = false;
          finishedXML = true;
        }
        break;
      case XMLEvent.END_DOCUMENT:
        inDocument = false;
        finishedXML = true;
        break;
      }
    }

    return collectionBuilder.build();
  }

  /**
   * Starting writing an XML document.
   * 
   * @param out Writer to write XML to
   * @param collection collection to write
   * 
   *          Since this class is for document at a time IO, any documents in
   *          the collection are ignored.
   * @throws XMLStreamException 
   */
  public void startWrite(Writer out, BioCCollection collection)
      throws XMLStreamException {
    
    // ?? if filename == '-', write to Print.out ??
    XMLOutputFactory xof = XMLOutputFactory.newInstance();
    xtw = null;
    xtw = (XMLStreamWriter2) xof.createXMLStreamWriter(out);
    // new FileWriter(filename));

    // xtw.writeStartBioCDocument(null,"1.0");
    xtw.writeStartDocument();
    xtw.writeDTD("collection", "org.biocreative.bioc.dtd", null, null);
    xtw.writeStartElement("collection");
    writeXML("source", collection.getSource());
    writeXML("date", collection.getDate());
    writeXML("key", collection.getKey());
    writeXML(collection.getInfons());

  }

  public String toXML(BioCCollection collection)
      throws Exception {
    XMLOutputFactory xof = XMLOutputFactory.newInstance();
    StringWriter xml = new StringWriter();
    xtw = (XMLStreamWriter2) xof.createXMLStreamWriter(xml);
    xtw.writeStartDocument();
    xtw.writeDTD("collection", "org.biocreative.bioc.dtd", null, null);
    xtw.writeStartElement("collection");
    writeXML("source", collection.getSource());
    writeXML("date", collection.getDate());
    writeXML("key", collection.getKey());
    writeXML(collection.getInfons());
    // /////////////////////////////////
    // Now, cycle thru each Document
    // /////////////////////////////////
    List<BioCDocument> readDocuments = collection.getDocuments();
    for (int i = 0; i < readDocuments.size(); i++) {
      BioCDocument readDocument = readDocuments.get(i);
      writeNext(readDocument);
    }
    endWrite();
    return xml.toString();
  }

  /**
   * Write the next document to the XML file.
   * 
   * @param document document to write
   * @throws XMLStreamException 
   */
  public void writeNext(BioCDocument document)
      throws XMLStreamException {
    writeXML(document);
  }

  void writeXML(BioCAnnotation annotation)
      throws XMLStreamException {
    xtw.writeStartElement("annotation");
    if (annotation.getID().length() > 0) {
      xtw.writeAttribute("id", annotation.getID());
    }
    writeXML(annotation.getInfons());
    for (BioCLocation location : annotation.getLocations()) {
      writeXML(location);
    }
    writeXML("text", annotation.getText());
    xtw.writeEndElement();
  }

  void writeXML(BioCDocument document)
      throws XMLStreamException {
    xtw.writeStartElement("document");
    writeXML("id", document.getID());
    writeXML(document.getInfons());
    for (BioCPassage passage : document.getPassages()) {
      writeXML(passage);
    }
    for (BioCRelation relation : document.getRelations()) {
    	writeXML(relation);
    }
    xtw.writeEndElement();
  }

  void writeXML(BioCLocation location)
      throws XMLStreamException {
    xtw.writeStartElement("location");
    xtw.writeAttribute("offset", Integer.toString(location.getOffset()));
    xtw.writeAttribute("length", Integer.toString(location.getLength()));
    xtw.writeEndElement();
  }

  void writeXML(BioCNode node)
      throws XMLStreamException {
    xtw.writeStartElement("node");
    xtw.writeAttribute("refid", node.getRefid());
    xtw.writeAttribute("role", node.getRole());
    xtw.writeEndElement();
  }

  void writeXML(BioCPassage passage)
      throws XMLStreamException {
    xtw.writeStartElement("passage");
    writeXML(passage.getInfons());
    writeXML("offset", passage.getOffset());
    if (passage.getText().isPresent()) {
      writeXML("text", passage.getText().get());
    }
    for (BioCSentence sentence : passage.getSentences()) {
      writeXML(sentence);
    }
    for (BioCAnnotation annotation : passage.getAnnotations()) {
      writeXML(annotation);
    }
    for (BioCRelation relation : passage.getRelations()) {
      writeXML(relation);
    }
    xtw.writeEndElement();
  }

  void writeXML(BioCRelation relation)
      throws XMLStreamException {
    xtw.writeStartElement("relation");
    if (relation.getID().length() > 0) {
      xtw.writeAttribute("id", relation.getID());
    }
    writeXML(relation.getInfons());
    for (BioCNode node : relation.getNodes()) {
      writeXML(node);
    }
    xtw.writeEndElement();
  }

  void writeXML(BioCSentence sentence)
      throws XMLStreamException {
    xtw.writeStartElement("sentence");
    writeXML(sentence.getInfons());
    writeXML("offset", sentence.getOffset());
    if (sentence.getText().isPresent()) {
      writeXML("text", sentence.getText().get());
    }
    for (BioCAnnotation annotation : sentence.getAnnotations()) {
      writeXML(annotation);
    }
    for (BioCRelation relation : sentence.getRelations()) {
      writeXML(relation);
    }
    xtw.writeEndElement();
  }

  void writeXML(Map<String, String> infons)
      throws XMLStreamException {
    for (Entry<String, String> entry : infons.entrySet()) {
      xtw.writeStartElement("infon");
      xtw.writeAttribute("key", entry.getKey());
      xtw.writeCharacters(entry.getValue());
      xtw.writeEndElement();
    }
  }

  void writeXML(String element, int contents)
      throws XMLStreamException {
    writeXML(element, new Integer(contents).toString());
  }

  void writeXML(String element, String contents)
      throws XMLStreamException {
    xtw.writeStartElement(element);
    xtw.writeCharacters(contents);
    xtw.writeEndElement();
  }

}
