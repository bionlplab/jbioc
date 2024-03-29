# Another Java implementation of BioC

Data structures and code to read/write BioC XML.

### BioC

BioC XML format can be used to share text documents and annotations.
The development of Java BioC IO API is independent of the particular XML parser used.

### Getting started

```XML
<dependency>
  <groupId>com.pengyifan.bioc</groupId>
  <artifactId>pengyifan-bioc</artifactId>
  <version>1.0.2</version>
</dependency>
```

or

```XML
<repositories>
    <repository>
        <id>oss-sonatype</id>
        <name>oss-sonatype</name>
        <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
...
<dependency>
  <groupId>com.pengyifan.bioc</groupId>
  <artifactId>pengyifan-bioc</artifactId>
  <version>1.0.3-SNAPSHOT</version>
</dependency>
```

### Developers

* Yifan Peng (yip4002@med.cornell.edu)

### Acknowledgment

* Don Comeau
* Rezarta Islamaj Dogan
* Haibin Liu 
* Thomas C. Wiegers
* John Wilbur

### Webpage

The official BioC webpage is available with all up-to-date instructions, code, and corpora in the BioC format, and other research on, based on and related to BioC. 

* [http://www.ncbi.nlm.nih.gov/CBBresearch/Dogan/BioC/](http://www.ncbi.nlm.nih.gov/CBBresearch/Dogan/BioC/)
* [http://bioc.sourceforge.net/](http://bioc.sourceforge.net/)

### Reference

* Comeau,D.C., Doğan,R.I., Ciccarese,P., Cohen,K.B., Krallinger,M., Leitner,F., Lu,Z., Peng,Y., Rinaldi,F., Torii,M., Valencia,V., Verspoor,K., Wiegers,T.C., Wu,C.H., Wilbur,W.J. (2013) [BioC: A minimalist approach to interoperability for biomedical text processing](http://database.oxfordjournals.org/content/2013/bat064.abstract). *Database: The Journal of Biological Databases and Curation*.
* Peng,Y., Tudor,C., Torii,M., Wu,C.H., Vijay-Shanker,K. (2014) [iSimp in BioC standard format: Enhancing the interoperability of a sentence simplification system](http://database.oxfordjournals.org/content/2014/bau038). *Database: The Journal of Biological Databases and Curation*.
