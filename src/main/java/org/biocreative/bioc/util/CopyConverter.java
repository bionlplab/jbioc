package org.biocreative.bioc.util;

import org.biocreative.bioc.BioCAnnotation;
import org.biocreative.bioc.BioCCollection;
import org.biocreative.bioc.BioCDocument;
import org.biocreative.bioc.BioCPassage;
import org.biocreative.bioc.BioCRelation;
import org.biocreative.bioc.BioCSentence;

/**
* Copies a {@code org.biocreative.bioc} data tree. Useful as a base class for processing
* that copies a few higher levels, but then performs interesting processing
* at a lower level. Not directly useful. Copy constructors are better.
*
* Use the methods in this close to copy any higher levels in the org.biocreative.bioc data
* structures that should be the same before and after processing. Override the
* appropriate method(s) to provide the desired transformation.
*
* Not part of the {@code org.biocreative.bioc} proposal. The analog class has been useful in
* C++ {@code org.biocreative.bioc} modules.
*/
public class CopyConverter {
	
	/**
	* Copy a {@code BioCCollection}.
	*/
	
	public BioCCollection getCollection(BioCCollection in) {
		BioCCollection out = new BioCCollection();
		out.setDate( in.getDate() );
		out.setSource( in.getSource() );
		out.setKey( in.getKey() );
		out.setInfons( in.getInfons() );
	
		for ( BioCDocument doc : in.getDocuments() ) {
			out.addDocument( getDocument(doc) );
		}
		return out;
	}
	
	/**
	* Copy a {@code BioCDocument}.
	*/
	public BioCDocument getDocument(BioCDocument in) {
		BioCDocument out = new BioCDocument();
		out.setID( in.getID() );
		out.setInfons( in.getInfons() );
		for ( BioCPassage passage : in.getPassages() ) {
			out.addPassage( getPassage(passage) );
		}
		for (BioCRelation rel : in.getRelations() ) {
			out.addRelation( rel );
		}
	
		return out;
	}
	
	/**
	* Copy a {@code BioCPassage}.
	*/
	public BioCPassage getPassage(BioCPassage in) {
		BioCPassage out = new BioCPassage();
		out.setOffset( in.getOffset() );
		out.setText( in.getText() );
		out.setInfons( in.getInfons() );
		for ( BioCSentence sentence : in.getSentences() ) {
			out.addSentence( getSentence(sentence) );
		}
		for (BioCAnnotation note : in.getAnnotations() ) {
			out.addAnnotation( getAnnotation(note) );
		}
		for (BioCRelation rel : in.getRelations() ) {
			out.addRelation( rel );
		}
	
		return out;
	}
	
	/**
	* Copy a {@code BioCSentence}.
	*/
	public BioCSentence getSentence(BioCSentence in) {
		BioCSentence out = new BioCSentence();
		out.setOffset( in.getOffset() );
		out.setText( in.getText() );
		out.setInfons( in.getInfons() );
	
		for (BioCAnnotation note : in.getAnnotations() ) {
			out.addAnnotation( getAnnotation(note) );
		}
		for (BioCRelation rel : in.getRelations() ) {
			out.addRelation( rel );
		}
	
		return out;
	}
	
	/**
	  * Copy a {@code BioCAnnotation}.
	  */
	  public BioCAnnotation getAnnotation(BioCAnnotation in) {
	    BioCAnnotation out = new BioCAnnotation();
	    out.setID(in.getID());
	    out.setInfons(in.getInfons());
	    out.setText(in.getText());
	    out.setLocations(in.getLocations());
	    
	    return out;
	  }
}
