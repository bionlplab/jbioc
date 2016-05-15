package com.pengyifan.bioc.validation;

import java.io.File;
import java.io.IOException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

public interface BioCValidator {
  void validate(File file);
  BioCErrorHandler getErrorHandler();
  void setErrorHandler(BioCErrorHandler errorHandler);
}
