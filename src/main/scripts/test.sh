#/bin/sh
./Print_XML.jar xml/collection.xml | diff output/collection.out -
./Copy_XML.jar xml/abbr.xml output/abbr.xml passage_relation.dtd
diff xml/abbr.xml output/abbr.xml
./Copy_XML.jar xml/ascii.xml output/ascii.xml collection.dtd
diff xml/ascii.xml output/ascii.xml
./Copy_XML.jar xml/collection.xml output/collection.xml collection.dtd
diff xml/collection.xml output/collection.xml
./Copy_XML.jar xml/sentence.xml output/sentence.xml sentence.dtd
diff xml/sentence.xml output/sentence.xml
./Copy_XML.jar xml/sentence_annotation.xml output/sentence_annotation.xml passage_annotation.dtd
diff xml/sentence_annotation.xml output/sentence_annotation.xml
./Copy_XML.jar xml/tokens.xml output/tokens.xml sentence_annotation.dtd
diff xml/tokens.xml output/tokens.xml
./Copy2_XML.jar xml/abbr.xml output/abbr.xml passage_relation.dtd
diff xml/abbr.xml output/abbr.xml
./Copy2_XML.jar xml/ascii.xml output/ascii.xml collection.dtd
diff xml/ascii.xml output/ascii.xml
./Copy2_XML.jar xml/collection.xml output/collection.xml collection.dtd
diff xml/collection.xml output/collection.xml
./Copy2_XML.jar xml/sentence.xml output/sentence.xml sentence.dtd
diff xml/sentence.xml output/sentence.xml
./Copy2_XML.jar xml/sentence_annotation.xml output/sentence_annotation.xml passage_annotation.dtd
diff xml/sentence_annotation.xml output/sentence_annotation.xml
./Copy2_XML.jar xml/tokens.xml output/tokens.xml sentence_annotation.dtd
diff xml/tokens.xml output/tokens.xml
./SentenceSplit.jar xml/ascii.xml output/sentence.xml sentence.dtd
diff xml/sentence.xml output/sentence.xml
