// package bioc.test;
//
// /*
// * Split passages into sentences. This is a demonstration of what a useful
// tool
// * would look like, not a genuine tool. It uses the naive period-space
// pattern
// * to split sentences, not a genuine algorithm as in the C++ tool.
// */
//
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.HashMap;
//
// import bioc.BioCCollection;
// import bioc.BioCDocument;
// import bioc.BioCPassage;
// import bioc.BioCSentence;
// import bioc.io.nih.ConnectorWoodstox;
//
// class SentenceConverter extends CopyConverter {
//
// @Override
// public BioCPassage getPassage(BioCPassage in) {
// BioCPassage out = new BioCPassage();
// out.offset = in.offset;
// out.infons = new HashMap<String, String>(in.infons);
//
// int current = 0;
// int period = in.text.indexOf(". ", current);
// while (period > -1) {
//
// BioCSentence sentence = new BioCSentence();
// sentence.offset = out.offset + current;
// sentence.text = in.text.substring(current, period + 1);
// out.sentences.add(sentence);
//
// current = period + 2; // advance to next sentence
// while (current < in.text.length() && in.text.charAt(current) == ' ') {
// ++current; // skip extra spaces
// }
// if (current >= in.text.length()) {
// current = -1; // flag if fell off end
// period = -1;
// } else {
// period = in.text.indexOf(". ", current);
// }
// }
//
// if (current > -1) {
// BioCSentence sentence = new BioCSentence();
// sentence.offset = out.offset + current;
// sentence.text = in.text.substring(current);
// out.sentences.add(sentence);
// }
//
// return out;
// }
// }
//
// public class SentenceSplit {
//
// public static void main(String[] args)
// throws IOException {
//
// if (args.length != 2) {
// System.err.println("usage: java --jar SentenceSplit in.xml out.xml");
// System.exit(-1);
// }
//
// SentenceSplit split = new SentenceSplit();
// split.split(args[0], args[1]);
// }
//
// public void split(String inXML, String outXML)
// throws IOException {
//
// ConnectorWoodstox inConnector = new ConnectorWoodstox();
// BioCCollection collection = inConnector.startRead(new FileReader(inXML));
//
// ConnectorWoodstox outConnector = new ConnectorWoodstox();
// SentenceConverter converter = new SentenceConverter();
// BioCCollection outCollection = converter.getCollection(collection);
// outCollection.key = "sentence.key";
// outConnector.startWrite(new FileWriter(outXML), outCollection);
//
// while (inConnector.hasNext()) {
// BioCDocument document = inConnector.next();
// BioCDocument outDocument = converter.getDocument(document);
// outConnector.writeNext(outDocument);
// }
//
// outConnector.endWrite();
// }
// }
