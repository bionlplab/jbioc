package bioc.test.junit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;

import org.custommonkey.xmlunit.Validator;
import org.custommonkey.xmlunit.exceptions.ConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;

public class BioCValidation {

  String localDTD = "xml/BioC.dtd";

  @Test
  public void test()
      throws ConfigurationException, FileNotFoundException, SAXException,
      MalformedURLException {
    test(new File("xml/PMID-8557975-simplified-sentences-tokens.xml"));
    test(new File("xml/PMID-8557975-simplified-sentences.xml"));
  }

  private void test(File file)
      throws FileNotFoundException, ConfigurationException, SAXException,
      MalformedURLException {
    FileReader reader = new FileReader(file);
    File dtd = new File(localDTD);
    Validator v = new Validator(reader, dtd.toURI().toURL().toString());
    v.assertIsValid();
  }
}
