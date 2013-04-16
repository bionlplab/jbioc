Java implementation of BioC
Version 0.2
Data structures and code to read / write XML.

This is a first preliminary Java version. While there has been an
effort to follow customary Java idioms, the author has not been an
active Java developer for a number of years. Suggestions and advice
are welcome.

For example, there are no getters and setters in the core BioC classes
to emphasize that these are data structures, not classes with
behaviors. These missing methods could easily be added.

The current XML input / output class uses the Woodstox XML
parser. This is a Streaming API for XML (StAX) parser. It provides
many of the advantages of DOM parsers where desired combined with the
low memory use of SAX parsers. 

The command
  make
builds the needed class files, jar files, and the Javadoc files. The
command
  make test
runs a simple shell script that demonstrates how to run the programs
and verifies that they are working.  No output means all is
well. Unfortunately, if there are problems the output will be large
and unclear.

The manifest.txt file only allows the executables to work in the same
directory. Eclipse offers more flexible ways to build executable jar
files.

The sample programs are weak. More interesting examples are being
developed. Hopefully they demonstrate the flavor of using BioC for
reading and writing data.

Print_XML.jar xml_file
  prints a simple text version of the XML file

Copy_XML.jar input_xml_file output_xml_file dtd_file
  reads data from input_xml_file and immediately writes it to
  output_xml_file

Copy2_XML.jar input_xml_file output_xml_file dtd_file
  reads data from input_xml_file, copies it using CopyConverter and
  then writes it to output_xml_file

SentenceSplit.jar input_xml_file output_xml_file sentence_dtd_file
  Segments sentences using the naive ". " algorithm. This algorithm
  happens to be adequate for the few sample PubMed references.
