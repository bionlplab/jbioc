package com.pengyifan.bioc.validation;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

public class BioCSchemaValidator implements BioCValidator {

  private final Validator validator;
  private BioCErrorHandler errorHandler;

  public BioCSchemaValidator(File schemaFile) throws SAXException {
    this(schemaFile, new BioCErrorHanlderImpl());
  }

  public BioCSchemaValidator(File schemaFile, BioCErrorHandler errorHandler) throws SAXException {
    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = schemaFactory.newSchema(schemaFile);
    validator = schema.newValidator();
    this.errorHandler = errorHandler;
  }

  @Override
  public void validate(File file) {
    try {
      validator.validate(new StreamSource(file));
    } catch (IOException e) {
      getErrorHandler().error(e);
    }catch (SAXException e) {
      getErrorHandler().error(e);
    }
  }

  @Override
  public BioCErrorHandler getErrorHandler() {
    return errorHandler;
  }

  @Override
  public void setErrorHandler(BioCErrorHandler errorHandler) {
    this.errorHandler = errorHandler;
  }
}