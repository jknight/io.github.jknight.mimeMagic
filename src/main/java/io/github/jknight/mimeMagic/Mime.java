package io.github.jknight.mimeMagic;

import lombok.extern.log4j.Log4j2;
import java.io.IOException;
import java.util.*;

@Log4j2
public class Mime
{
    private List<MimeType> mimeTypes = null;
    private final String propertiesFileName = "mime.properties";

    public Mime() throws IOException, IllegalArgumentException
    {
        this.mimeTypes = Load.mimeTypes(propertiesFileName);
    }

    public MimeType fromFileName(final String fileName)
    {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        return this.fromExtension(fileExtension);
    }

    public MimeType fromExtension(final String extension)
    {
        if(extension == null || extension.length() == 0) return MimeType.unknown;

        for(final MimeType mimeType : mimeTypes)
        {
            if(mimeType.Extension.equalsIgnoreCase(extension))
                return mimeType;
        }

        return MimeType.unknown;
    }

    /***
     * Note: this method does *not* yet identify plain ascii text files because plain text files
     *      don't have 'magic' bytes. If you know your text files always start with the same bytes
     *      (eg an xml file) then you could use this but otherwise, this is intended for binary file
     *      detection.
     * @param bytes
     * @return
     */
    public MimeType fromHeaderBytes(final byte[] bytes)
    {
        for(final MimeType mimeType : mimeTypes)
        {
            byte[] magicBytes = mimeType.MagicBytes;
            if (arrayStartsWith(bytes, magicBytes))
                return mimeType;
        }
        return MimeType.unknown;
    }

    private boolean arrayStartsWith(final byte[] target, final byte[] test)
    {
        if (target.length < test.length) return false;

        for (int i = 0; i < test.length; i++)
            if (target[i] != test[i]) return false;

        return true;
    }
}
