/**
 * BioC is a collection of data structures to represent data often used in
 * natural language text processing. These data structures are defined in Java.
 * <p>
 * These data structures are also defined in XML files via DTDs. Using these
 * XML files, the data can be transferred from one program to another
 * independent of language or research group.
 * <p>
 * All BioC XML files, or collections, begin with a {@link BioCCollection} . A
 * {@code BioCCollection} contains a number of {@code BioCDocument}s. Each
 * {@link BioCDocument} consistents of a series of {@code BioCPassage}s. Each
 * {@link BioCPassage} may contain either {@code text}, a series of
 * {@code BioCSentence}s, or possibly {@code BioCAnnotation}s and
 * {@code BioCRelation}s. A {@link BioCSentence} has either a series of
 * {@code BioCSentence}s, or possibly {@code BioCAnnotation}s and
 * {@code BioCRelation}s. An {@link BioCAnnotation} identifies a portion of the
 * {@code text} and an appropriate {@code type} for that {@code text}. A
 * {@link BioCRelation} contains {@code refId}s to other {@code BioCRelation} s
 * and {@code BioCAnnotation}s and a {@code type} and {@code label}s to
 * describe the relationship between them.
 */
package org.biocreative.bioc;

