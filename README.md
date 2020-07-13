# io.github.jknight.mimeMagic

<<NO LONGER IN USE>>
Use a stripped down version of [Apache Tikka](https://tika.apache.org/) instead

---

A simple configuration-based library for determining mime types based on file header bytes.

This library is light weight with a simple [properties file](src/main/resources/mime.properties) that maps magic numbers to mime/extension.

For a more comprehensive Mime library, see [apache tika](https://tika.apache.org/)

mimeMagic Features:

* File bytes -> mime (fromHeaderBytes)  
  public MimeType fromHeaderBytes(final byte[] bytes)
* mime -> extension  
  public MimeType fromMime(final String mime)
* extension -> mime  
  public MimeType fromExtension(final String extension)
* file name -> mime  
  public MimeType fromFileName(final String fileName)

## Install:
 mvn install:install-file -Dfile=mimeMagic-1.0.jar -DgroupId=io.github.jknight -DartifactId=mimeMagic -Dversion=1.0  -Dpackaging=Jar
