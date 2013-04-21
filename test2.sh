#/bin/sh
CLASSPATH='-classpath bin:lib/stax2-api-3.1.1.jar:lib/woodstox-core-asl-4.1.4.jar:lib/xmlunit-1.4.jar:lib/junit-4.11.jar:lib/hamcrest-core-1.3.jar'
SOURCEPATH='-sourcepath src'

echo "compile..."
find ./src -name *.java > sources_list.txt
javac -g -d bin $CLASSPATH $SOURCEPATH @sources_list.txt
rm -rf sources_list.txt

echo "Test BioCCollectionReader and BioCCollectionWriter"
java $CLASSPATH org.junit.runner.JUnitCore bioc.test.junit.BioCCollectionWriterTest

echo "Test BioCDocumentReader and BioCDocumentWriter"
java $CLASSPATH org.junit.runner.JUnitCore bioc.test.junit.BioCDocumentWriterTest
