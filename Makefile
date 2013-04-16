
.SUFFIXES:
.SUFFIXES: .class .java .jar

JC = javac

CLASSPATH='stax2-api-3.1.1.jar:woodstox-core-asl-4.1.4.jar:.'

BIOC_CLASSES= bioc/Annotation.class \
	bioc/Collection.class \
	bioc/ConnectorWoodstox.class \
	bioc/Document.class \
	bioc/Passage.class \
	bioc/Relation.class \
	bioc/Sentence.class

JAR_LIST=stax2-api-3.1.1.jar \
	woodstox-core-asl-4.1.4.jar \
	bioc.jar

%.class: %.java
	$(JC) -cp $(CLASSPATH) $<

all: jars doc

jars: bioc.jar SentenceSplit.jar Copy_XML.jar Copy2_XML.jar Print_XML.jar

doc:
	javadoc -d doc -classpath $(CLASSPATH) bioc

bioc.jar: $(BIOC_CLASSES)
	jar cf bioc.jar $(BIOC_CLASSES)

%.jar:	%.class CopyConverter.class manifest.txt $(JAR_LIST)
	jar cfme $@ manifest.txt $* $*.class CopyConverter.class
	chmod +x $@

SentenceSplit.jar: SentenceSplit.class CopyConverter.class manifest.txt \
                   $(JAR_LIST)
	jar cfme $@ manifest.txt $* $*.class CopyConverter.class SentenceConverter.class
	chmod +x $@

test:
	./test.sh
