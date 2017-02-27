package io.github.jknight.mimeMagic;

public class MimeType
{
    public String Extension;
    public String Mime;
    public byte[] MagicBytes;

    public static MimeType unknown = new MimeType()
    {{
        Extension = "bin";
        Mime="unknown/unknown";
    }};
}
