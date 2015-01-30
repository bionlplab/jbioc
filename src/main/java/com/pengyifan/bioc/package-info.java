/**
 * Defines data structures of BioC.
 * <p>
 * BioC is a collection of data structures to represent data often used in
 * natural language text processing. These data structures are defined in Java.
 * <p>
 * These data structures are also defined in XML files via DTDs. Using these
 * XML files, the data can be transferred from one program to another
 * independent of language or research group.
 * <p>
 * All BioC XML files, or collections, begin with a {@link BioCCollection}. A
 * {@link com.pengyifan.bioc.BioCCollection} contains a number of
 * {@link com.pengyifan.bioc.BioCDocument}s. Each
 * {@link com.pengyifan.bioc.BioCDocument} consistent of a series of
 * {@link com.pengyifan.bioc.BioCPassage}s. Each
 * {@link com.pengyifan.bioc.BioCPassage} may contain either {@code text}, a
 * series of {@link com.pengyifan.bioc.BioCSentence}s, or possibly
 * {@link com.pengyifan.bioc.BioCAnnotation} s and
 * {@link com.pengyifan.bioc.BioCRelation}s. A
 * {@link com.pengyifan.bioc.BioCSentence} has either a series of
 * {@code com.pengyifan.bioc.BioCSentence}s, or possibly
 * {@link com.pengyifan.bioc.BioCAnnotation} s and
 * {@link com.pengyifan.bioc.BioCRelation}s. An
 * {@link com.pengyifan.bioc.BioCAnnotation} identifies a portion of the
 * {@code text} and an appropriate {@code type} for that {@code text}. A
 * {@link com.pengyifan.bioc.BioCRelation} contains {@code refId}s to other
 * {@link com.pengyifan.bioc.BioCRelation} s and
 * {@link com.pengyifan.bioc.BioCAnnotation}s and a {@code type} and
 * {@code label}s to describe the relationship between them.
 */
package com.pengyifan.bioc;

