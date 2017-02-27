package io.github.jknight.mimeMagic;

import lombok.extern.log4j.Log4j2;

import javax.xml.bind.DatatypeConverter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Log4j2
/*package-protected*/ final class Load
{

	public static List<MimeType> mimeTypes(final String config_properties) throws IOException
	{
		Properties properties = new Properties();
		try (InputStream inputStream = Mime.class.getClassLoader().getResourceAsStream(config_properties))
		{
			if (inputStream == null)
			{
				throw new FileNotFoundException("configuration file '" + config_properties + "' not found in the classpath");
			}
			properties.load(inputStream);
		}

		List<MimeType> mimeTypes = new ArrayList<>();
		for (Map.Entry<Object, Object> entry : properties.entrySet())
		{
			String key = entry.getKey().toString(); //hex value
			String value = entry.getValue().toString(); //comma separated extension,mime
			String parts[] = value.split(",");

			if (parts.length < 2) throw new IllegalArgumentException("incorrect config in " + config_properties);

			byte[] magicBytes = DatatypeConverter.parseHexBinary(key);

			MimeType mimeType = new MimeType()
			{{
				Extension = parts[0].toLowerCase();
				Mime = parts[1].toLowerCase();
				MagicBytes = magicBytes;
			}};

			mimeTypes.add(mimeType);
		}
		return mimeTypes;
	}
}




