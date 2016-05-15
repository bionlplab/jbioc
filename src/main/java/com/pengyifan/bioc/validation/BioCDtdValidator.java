package com.pengyifan.bioc.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.custommonkey.xmlunit.Validator;
import org.xml.sax.SAXException;


public class BioCDtdValidator implements BioCValidator {

  private final File dtdFile;
  private BioCErrorHandler errorHandler;

  public BioCDtdValidator(File dtdFile) {
    this(dtdFile, new BioCErrorHanlderImpl());
  }

  public BioCDtdValidator(File dtdFile, BioCErrorHandler errorHandler) {
    this.dtdFile = dtdFile;
    this.errorHandler = errorHandler;
  }

  @Override
  public void validate(File file) {
    try {
      File temp = File.createTempFile(file.getName(), ".tmp");
      System.out.println("Generate BioC file with DTD: " + temp);
      // add dtd
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtdFile.getAbsolutePath());
      transformer.transform(new StreamSource(file), new StreamResult(temp));
      Validator v = new Validator(new FileReader(temp), dtdFile.getAbsolutePath());
      v.assertIsValid();
    } catch (FileNotFoundException e) {
      getErrorHandler().error(e);
    } catch (IOException e) {
      getErrorHandler().error(e);
    } catch (TransformerConfigurationException e) {
      getErrorHandler().error(e);
    } catch (SAXException e) {
      getErrorHandler().error(e);
    } catch (TransformerException e) {
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
