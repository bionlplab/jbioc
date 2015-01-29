/**
 * This package defines utility classes for working with readers and writers on
 * BioC files. The most commonly used classes are described here:
 * 
 * <p>
 * <b>BioCCollectionReader</b> reads the entire BioC file into one collection.
 * 
 * <p>
 * <b>BioCDocumentReader</b> sequentially reads the BioC file into document
 * every time the method
 * {@link com.pengyifan.bioc.io.BioCDocumentReader#readDocument} is called.
 * 
 * <p>
 * <b>BioCCollectionWriter</b> writes the
 * {@link com.pengyifan.bioc.BioCCollection} into a file.
 * 
 * <p>
 * <b>BioCDocumentWriter</b> sequentially writes
 * {@link com.pengyifan.bioc.BioCDocument}s into the file.
 */
package com.pengyifan.bioc.io;

