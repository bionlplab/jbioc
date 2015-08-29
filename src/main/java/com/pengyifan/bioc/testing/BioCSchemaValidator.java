package com.pengyifan.bioc.testing;

import com.google.common.collect.Lists;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Validate the BioC file via the command line.
 */
public class BioCSchemaValidator {

  @Option(name = "-h", help = true, usage = "print this help")
  private boolean help = false;

  @Option(name = "-s", usage = "set XML Schema file")
  private String schemaFilename = "BioC.xsd";

  @Argument
  private List<String> arguments = Lists.newArrayList();

  /**
   * BioCSchemaValidator [options...] arguments...
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    new BioCSchemaValidator().doMain(args);
  }

  private void printHelp(CmdLineParser parser) {
    System.err.println("java BioCSchemaValidator [options...] FILE...");
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

    File schemaFile = new File(schemaFilename);
    if (!schemaFile.exists()) {
      System.err.println("cannot find the XML schema file");
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
      } else if (isValid(biocFile, schemaFile)) {
        System.out.println(biocFile + " is valid");
      } else {
        System.out.println(biocFile + " is NOT valid");
      }
    }
  }

  /**
   * Asserts that a BioC file is valid based on the given XML schema file. If the BioC
   * file is invalid, prints the error message.
   *
   * @param biocFile   BioC file reader stream
   * @param schemaFile the file of the BioC DTD file
   */
  public boolean isValid(File biocFile, File schemaFile) {
    try {
      SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = schemaFactory.newSchema(schemaFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(biocFile));
      return true;
    } catch (SAXException e) {
      System.out.println("Reason: " + e.getLocalizedMessage());
      return false;
    } catch (IOException e) {
      System.out.println("Reason: " + e.getLocalizedMessage());
      return false;
    }
  }
}