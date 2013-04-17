Java implementation of BioC
Version 0.2
Data structures and code to read / write XML.

This is a first preliminary Java version. While there has been an
effort to follow customary Java idioms, the author has not been an
active Java developer for a number of years. Suggestions and advice
are welcome.

The current XML input / output class uses the Woodstox XML
parser. This is a Streaming API for XML (StAX) parser. It provides
many of the advantages of DOM parsers where desired combined with the
low memory use of SAX parsers. 

The command
  bash test2.sh
builds the needed class files, and runs a simple shell script that 
demonstrates how to run the programs
and verifies that they are working.  No output means all is
well. Unfortunately, if there are problems the output will be large
and unclear.

The manifest.txt file only allows the executables to work in the same
directory. Eclipse offers more flexible ways to build executable jar
files.

The sample programs are weak. More interesting examples are being
developed. Hopefully they demonstrate the flavor of using BioC for
reading and writing data.
