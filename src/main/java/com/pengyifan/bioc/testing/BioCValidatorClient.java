package com.pengyifan.bioc.testing;

import com.google.common.collect.Lists;
import com.pengyifan.bioc.validation.BioCDtdValidator;
import com.pengyifan.bioc.validation.BioCSchemaValidator;
import com.pengyifan.bioc.validation.BioCValidator;
import java.io.File;
import java.util.List;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;
import org.xml.sax.SAXException;

public class BioCValidatorClient {
  @Option(name = "-h", help = true, usage = "print this help")
  private boolean help = false;

  @Option(name = "-s", usage = "set XML Schema file")
  private String schemaFilename = null;

  @Option(name = "-d", usage = "set DTD file")
  private String dtdFilename = null;

  @Argument
  private List<String> arguments = Lists.newArrayList();

  public static void main(String[] args) throws SAXException {
    new BioCValidatorClient().doMain(args);
  }

  private void printHelp(CmdLineParser parser) {
    System.err.println("java BioCValidatorClient [options...] FILE...");
    parser.printUsage(System.err);
    System.err.println();
  }

  public void doMain(String[] args) throws SAXException {

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

    if (schemaFilename == null && dtdFilename == null) {
      System.err.println("XSD or DTD needs to specify.");
      printHelp(parser);
      return;
    }

    if (schemaFilename != null && dtdFilename != null) {
      System.err.println("XSD and DTD cannot specify at the same time.");
      printHelp(parser);
      return;
    }

    BioCValidator validator = null;
    if (schemaFilename != null) {
      validator = new BioCSchemaValidator(new File(schemaFilename));
    }
    if (dtdFilename != null) {
      validator = new BioCDtdValidator(new File(dtdFilename));
    }

    for (String biocFilename : arguments) {
      validator.validate(new File(biocFilename));
    }
  }
}
