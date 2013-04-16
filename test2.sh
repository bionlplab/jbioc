#/bin/sh
CLASSPATH='-classpath bin:lib/stax-utils.jar:lib/stax2-api-3.1.1.jar:lib/woodstox-core-asl-4.1.4.jar'
SOURCEPATH='-sourcepath src'

echo "compile..."
find ./src -name *.java > sources_list.txt
javac -g -d bin $CLASSPATH $SOURCEPATH @sources_list.txt
rm -rf sources_list.txt

inXML=xml/PMID-8557975-simplified-sentences-tokens.xml
out=output/PMID-8557975-simplified-sentences-tokens-copy

echo "Test BioCCollectionReader and BioCCollectionWriter"
java $CLASSPATH bioc.test.Copy5_XML $inXML $out-5.xml

echo "Test BioCDocumentReader and BioCDocumentWriter"
java $CLASSPATH bioc.test.Copy6_XML $inXML $out-6.xml
