package org.biocreative.bioc.util;

import static org.biocreative.bioc.io.BioCAssert.assertAndPrintDtdValid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;

import com.google.common.annotations.VisibleForTesting;

public class BioCDtdValidator {

  @Option(name = "-h", usage = "print this help")
  private boolean help = false;

  @Option(name = "-dtd", required = true, usage = "set DTD file")
  private String dtdFilename;

  @Argument
  private List<String> arguments = new ArrayList<String>();

  /**
   * BioCValidation DTD_file BioC_file ...
   * 
   * @throws FileNotFoundException
   */
  public static void main(String[] args) {
    new BioCDtdValidator().doMain(args);
  }

  @VisibleForTesting
  protected String getDtdFilename() {
    return dtdFilename;
  }

  @VisibleForTesting
  protected boolean getHelp() {
    return help;
  }

  @VisibleForTesting
  protected List<String> getArguments() {
    return arguments;
  }

  public void doMain(String[] args) {

    ParserProperties.defaults()
        .withUsageWidth(80);
    CmdLineParser parser = new CmdLineParser(this);

    try {
      parser.parseArgument(args);
    } catch (CmdLineException e) {
      // if there's a problem in the command line,
      // you'll get this exception. this will report
      // an error message.
      System.err.println(e.getMessage());
      System.err.println("java BioCValidation [options...] arguments...");
      // print the list of available options
      parser.printUsage(System.err);
      System.err.println();
      return;
    }

    if (help) {
      System.err.println("java BioCValidation [options...] arguments...");
      // print the list of available options
      parser.printUsage(System.err);
      System.err.println();
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
      } else {
        try {
          assertAndPrintDtdValid(
              new FileReader(biocFile),
              dtdFile.getAbsolutePath());
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        System.out.printf("testing %s: PASSED\n", biocFilename);
      }
    }
  }
}