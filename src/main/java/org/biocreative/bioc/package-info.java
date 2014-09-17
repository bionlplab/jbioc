/**
 * BioC is a collection of data structures to represent data often used in
 * natural language text processing. These data structures are defined in Java.
 * <p>
 * These data structures are also defined in XML files via DTDs. Using these
 * XML files, the data can be transferred from one program to another
 * independent of language or research group.
 * <p>
 * All BioC XML files, or collections, begin with a
 * {@link org.biocreative.bioc.BioCCollection} . A
 * {@link org.biocreative.bioc.BioCCollection} contains a number of
 * {@link org.biocreative.bioc.BioCDocument}s. Each
 * {@link org.biocreative.bioc.BioCDocument} consistents of a series of
 * {@link org.biocreative.bioc.BioCPassage}s. Each
 * {@link org.biocreative.bioc.BioCPassage} may contain either {@code text}, a
 * series of {@link org.biocreative.bioc.BioCSentence}s, or possibly
 * {@link org.biocreative.bioc.BioCAnnotation}s and
 * {@link org.biocreative.bioc.BioCRelation}s. A
 * {@link org.biocreative.bioc.BioCSentence} has either a series of
 * {@code BioCSentence}s, or possibly
 * {@link org.biocreative.bioc.BioCAnnotation} s and
 * {@link org.biocreative.bioc.BioCRelation}s. An
 * {@link org.biocreative.bioc.BioCAnnotation} identifies a portion of the
 * {@code text} and an appropriate {@code type} for that {@code text}. A
 * {@link org.biocreative.bioc.BioCRelation} contains {@code refId}s to other
 * {@link org.biocreative.bioc.BioCRelation} s and
 * {@link org.biocreative.bioc.BioCAnnotation}s and a {@code type} and
 * {@code label}s to describe the relationship between them.
 */
package org.biocreative.bioc;

