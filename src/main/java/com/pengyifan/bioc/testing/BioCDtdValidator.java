package com.pengyifan.bioc.testing;

import com.google.common.collect.Lists;
import junit.framework.AssertionFailedError;
import org.custommonkey.xmlunit.Validator;
import org.custommonkey.xmlunit.exceptions.ConfigurationException;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Validate the BioC file via the command line.
 */
public class BioCDtdValidator {

  @Option(name = "-h", help = true, usage = "print this help")
  private boolean help = false;

  @Option(name = "-dtd", required = true, usage = "set DTD file")
  private String dtdFilename = "BioC.dtd";

  @Argument
  private List<String> arguments = Lists.newArrayList();

  /**
   * BioCValidation [options...] arguments...
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    new BioCDtdValidator().doMain(args);
  }

  private void printHelp(CmdLineParser parser) {
    System.err.println("java BioCValidator [options...] FILE...");
    parser.printUsage(System.err);
    System.err.println();
  }

  public void doMain(String[] args) {

    ParserProperties.defaults()
        .withUsageWidth(80);
    CmdLineParser parser = new CmdLineParser(this);

    try {
      parser.parseArgument(args);
    } catch (CmdLineException e) {
      System.err.println(e.getMessage());
      printHelp(parser);
      return;
    }

    if (help) {
      printHelp(parser);
      return;
    }

    File dtdFile = new File(dtdFilename);
    if (!dtdFile.exists()) {
      System.err.println("cannot find the DTD file");
      return;
    }

    if (arguments.isEmpty()) {
      System.err.println("No argument is given");
      return;
    }

    for (String biocFilename : arguments) {
      File biocFile = new File(biocFilename);
      if (!biocFile.exists()) {
        System.err.println("cannot read bioc file: " + biocFilename);
      } else if (isValid(biocFile, dtdFile)) {
        System.out.println(biocFile + " is valid");
      } else {
        System.out.println(biocFile + " is NOT valid");
      }
    }
  }

  /**
   * Asserts that a BioC file is valid based on the given DTD file. If the BioC
   * file is invalid, prints the error message.
   *
   * @param biocFile BioC file
   * @param dtdFile  BioC DTD file
   */
  public boolean isValid(File biocFile, File dtdFile) {

    try {
      File temp = File.createTempFile(biocFile.getName(), ".tmp");
      System.out.println("Generate bioc file with DTD: " + temp);
      // add dtd
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtdFile.getAbsolutePath());
      transformer.transform(new StreamSource(biocFile), new StreamResult(temp));

      Validator v = new Validator(new FileReader(temp), dtdFile.getAbsolutePath());
      v.assertIsValid();
      return true;
    } catch (ConfigurationException e) {
      e.printStackTrace();
      return false;
    } catch (SAXException e) {
      e.printStackTrace();
      return false;
    } catch (AssertionFailedError e) {
      System.out.println(e.getMessage());
      return false;
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
      return false;
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return false;
    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
      return false;
    } catch (TransformerException e) {
      System.out.println(e.getMessage());
      return false;
    }
  }
}