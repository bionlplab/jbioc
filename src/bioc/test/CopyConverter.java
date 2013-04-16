// package bioc.test;
// // package bioc.util;
//
// import java.util.HashMap;
//
// import bioc.BioCAnnotation;
// import bioc.BioCCollection;
// import bioc.BioCDocument;
// import bioc.BioCPassage;
// import bioc.BioCRelation;
// import bioc.BioCSentence;
//
// /**
// * Copies a {@code BioC} data tree. Useful as a base class for processing
// * that copies a few higher levels, but then performs interesting processing
// * at a lower level. Not directly useful. Copy constructors are better.
// *
// * Use the methods in this close to copy any higher levels in the BioC data
// * structures that should be the same before and after processing. Override
// the
// * appropriate method(s) to provide the desired transformation.
// *
// * Not part of the {@code BioC} proposal. The analog class has been useful in
// * C++ {@code BioC} modules.
// */
// public class CopyConverter {
//
// /**
// * Copy a {@code BioCCollection}.
// */
//
// public BioCCollection getCollection(BioCCollection in) {
// BioCCollection out = new BioCCollection();
// out.date = in.date;
// out.source = in.source;
// out.key = in.key;
// out.infons = new HashMap<String, String>(in.infons);
//
// for ( BioCDocument doc : in.documents ) {
// out.documents.add( getDocument(doc) );
// }
// return out;
// }
//
// /**
// * Copy a {@code BioCDocument}.
// */
// public BioCDocument getDocument(BioCDocument in) {
// BioCDocument out = new BioCDocument();
// out.id = in.id;
// out.infons = new HashMap<String, String>(in.infons);
// for ( BioCPassage passage : in.passages ) {
// out.passages.add( getPassage(passage) );
// }
//
// return out;
// }
//
// /**
// * Copy a {@code BioCPassage}.
// */
// public BioCPassage getPassage(BioCPassage in) {
// BioCPassage out = new BioCPassage();
// out.offset = in.offset;
// out.text = in.text;
// out.infons = new HashMap<String, String>(in.infons);
// for ( BioCSentence sentence : in.sentences ) {
// out.sentences.add( getSentence(sentence) );
// }
// for (BioCAnnotation note : in.annotations) {
// out.annotations.add(new BioCAnnotation(note));
// }
// for (BioCRelation rel : in.relations) {
// out.relations.add(new BioCRelation(rel));
// }
//
// return out;
// }
//
// /**
// * Copy a {@code BioCSentence}.
// */
// public BioCSentence getSentence(BioCSentence in) {
// BioCSentence out = new BioCSentence();
// out.offset = in.offset;
// out.text = in.text;
//
// for (BioCAnnotation note : in.annotations) {
// out.annotations.add(new BioCAnnotation(note));
// }
// for (BioCRelation rel : in.relations) {
// out.relations.add(new BioCRelation(rel));
// }
//
// return out;
// }
// }
