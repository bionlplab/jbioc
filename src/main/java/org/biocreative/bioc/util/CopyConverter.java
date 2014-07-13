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
* 
* @deprecated use constructors or builders instead
*/
@Deprecated
public class CopyConverter {
	
	/**
	* Copy a {@code BioCCollection}.
	*/
	
	public BioCCollection getCollection(BioCCollection in) {
		return in.getBuilder().build();
	}
	
	/**
	* Copy a {@code BioCDocument}.
	*/
	public BioCDocument getDocument(BioCDocument in) {
	  return in.getBuilder().build();
	}
	
	/**
	* Copy a {@code BioCPassage}.
	*/
	public BioCPassage getPassage(BioCPassage in) {
	  return in.getBuilder().build();
	}
	
	/**
	* Copy a {@code BioCSentence}.
	*/
	public BioCSentence getSentence(BioCSentence in) {
	  return in.getBuilder().build();
	}
	
	/**
	  * Copy a {@code BioCAnnotation}.
	  */
	  public BioCAnnotation getAnnotation(BioCAnnotation in) {
	    return in.getBuilder().build();
	  }
}
