/**
 * BioC is a collection of data structures to represent data often used in
 * natural language text processing. These data structures are defined in Java.
 * <p>
 * These data structures are also defined in XML files via DTDs. Using these
 * XML files, the data can be transferred from one program to another
 * independent of language or research group.
 * <p>
 * All BioC XML files, or collections, begin with a {@link BioCCollection}. A
 * {@link BioCCollection} contains a number of {@link BioCDocument}s. Each
 * {@link BioCDocument} consistent of a series of {@link BioCPassage}s. Each
 * {@link BioCPassage} may contain either {@code text}, a series of
 * {@link BioCSentence}s, or possibly {@link BioCAnnotation}s and
 * {@link BioCRelation}s. A {@link BioCSentence} has either a series of
 * {@code BioCSentence}s, or possibly {@link BioCAnnotation} s and
 * {@link BioCRelation}s. An {@link BioCAnnotation} identifies a portion of the
 * {@code text} and an appropriate {@code type} for that {@code text}. A
 * {@link BioCRelation} contains {@code refId}s to other {@link BioCRelation} s
 * and {@link BioCAnnotation}s and a {@code type} and {@code label}s to
 * describe the relationship between them.
 */
package com.pengyifan.nlp.bioc;

