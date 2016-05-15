package com.pengyifan.bioc.validation;

import java.io.IOException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

public interface BioCErrorHandler {
  void error(IOException e);
  void error(SAXException e);
  void error(TransformerException e);
}
