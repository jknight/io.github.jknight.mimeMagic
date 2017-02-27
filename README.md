# io.github.jknight.mimeMagic


A simple configuration-based library for determining mime types based on file header bytes.

Features:

* File bytes -> mime (fromHeaderBytes)  
  public MimeType fromHeaderBytes(final byte[] bytes)
* mime -> extension  
  public MimeType fromMime(final String mime)
* extension -> mime  
  public MimeType fromExtension(final String extension)
* file name -> mime  
  public MimeType fromFileName(final String fileName)
