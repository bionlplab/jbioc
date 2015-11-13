package com.pengyifan.bioc.testing;

import com.google.common.collect.Lists;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLStreamException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Compare the differences between two BioC files.
 */
public class BioCDiffClient {

  @Option(name = "-h", usage = "Give this help list")
  private boolean help = false;

  @Argument
  private List<String> arguments = Lists.newArrayList();

  public static void main(String[] args) throws IOException, XMLStreamException, SAXException {
    new BioCDiffClient().doMain(args);
  }

  private void printHelp(CmdLineParser parser) {
    System.err.println("Usage: java BioCDiffClient FILE1 FILE2");
    System.err.println("Compare two given files.");
    System.err.println();
    parser.printUsage(System.err);
    System.err.println();
  }

  private void doMain(String[] args) throws IOException, XMLStreamException, SAXException {
    ParserProperties properties = ParserProperties.defaults()
        .withUsageWidth(80);
    CmdLineParser parser = new CmdLineParser(this, properties);

    try {
      parser.parseArgument(args);
    } catch (CmdLineException e) {
      System.err.println(e.getMessage());
      printHelp(parser);
    }

    if (help) {
      printHelp(parser);
      return;
    }

    if (arguments.size() != 2) {
      System.err.println("Only two files are supported");
      printHelp(parser);
      return;
    }

    XMLUnit.setIgnoreWhitespace(true);
    XMLUnit.setIgnoreAttributeOrder(true);
    XMLUnit.setIgnoreComments(true);
    // XMLUnit.setCompareUnmatched(false);

    Diff diff = new Diff(new FileReader(arguments.get(0)), new FileReader(arguments.get(1)));
    diff.overrideElementQualifier(new RecursiveElementNameAndTextQualifier());
    System.out.println("Similar? " + diff.similar());

    DetailedDiff detailedDiff = new DetailedDiff(diff);
    List differences = detailedDiff.getAllDifferences();

    System.out.println("Total differences: " + differences.size());
    for (Object object : differences) {
      Difference difference = (Difference) object;
      System.out.println("------------------------------------------------------------");
      System.out.println(difference.toString());
      System.out.println(difference.getControlNodeDetail().getNode());
      System.exit(1);
    }
  }
}
