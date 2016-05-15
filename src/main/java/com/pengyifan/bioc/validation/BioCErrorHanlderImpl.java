package com.pengyifan.bioc.validation;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

public class BioCErrorHanlderImpl implements BioCErrorHandler {
  @Override
  public void error(IOException e) {
    e.printStackTrace();
  }

  @Override
  public void error(SAXException e) {
    e.printStackTrace();
  }

  @Override
  public void error(TransformerException e) {
    e.printStackTrace();
  }

  @Override
  public void error(XMLStreamException e) {
    e.printStackTrace();
  }

  @Override
  public void error(IllegalArgumentException e) {
    e.printStackTrace();
  }
}
