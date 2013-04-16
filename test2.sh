#/bin/sh
CLASSPATH='-classpath bin:lib/stax-utils.jar:lib/stax2-api-3.1.1.jar:lib/woodstox-core-asl-4.1.4.jar'

java $CLASSPATH bioc.test.Copy3_XML xml/PMID-8557975-simplified-sentences-tokens.xml output/PMID-8557975-simplified-sentences-tokens-copy3.xml