import io.github.jknight.mimeMagic.Mime;
import io.github.jknight.mimeMagic.MimeType;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MimeTests
{
    private Mime mime;

    @Before
    public void before() throws IOException
    {
        this.mime = new Mime();
    }

    //Load up sample images files and make sure they're detected correctly
    @Test
    public void test_files() throws IOException
    {
        Map<String, String> testCases = new HashMap<>();
        testCases.put("gif", "image/gif");
        testCases.put("tif", "image/tiff");
        testCases.put("jpg", "image/jpeg");
        testCases.put("pdf", "application/pdf");
        //testCases.put("text-cold", "application/x-cold");
        //testCases.put("mixed-cold", "application/x-cold");
        //testCases.put("dwg", "application/dwg");

        File samplesDirectory = new File("src/test/resources/ImageSamples/");
        for(val entry : testCases.entrySet())
        {
            String extension = entry.getKey();
            String expectedMime =  entry.getValue();

            val files = FileUtils.listFiles(samplesDirectory, new String[]{extension}, false);

            System.out.println("testing " + files.size() + " files of type " + extension + " ...");

            test_detect_files(files, expectedMime);
        }
    }

    private void test_detect_files(Collection<File> files, String expectedMime) throws IOException
    {
        for(File file : files)
        {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            val mimeType = this.mime.fromHeaderBytes(bytes);

            assertEquals(expectedMime, mimeType.Mime);
        }
    }

    @Test
    public void from_extension()
    {
        Map<String, String> testCases = new HashMap<>();
        testCases.put("gif", "image/gif");
        testCases.put("tif", "image/tiff");
        testCases.put("jpg", "image/jpeg");
        testCases.put("pdf", "application/pdf");
        for(val entry : testCases.entrySet())
        {
            MimeType mimeType = this.mime.fromExtension(entry.getKey());
            assertEquals(entry.getValue(), mimeType.Mime);
            assertNotNull(mimeType.MagicBytes);
        }
    }

    @Test
    public void from_file_name()
    {
        Map<String, String> testCases = new HashMap<>();
        testCases.put("hello.gif", "image/gif");
        testCases.put("123.gif.tif", "image/tiff");
        testCases.put("asdfsadfsdaf#24@#$#@$#@$JPg.pdf.jpg", "image/jpeg");
        testCases.put(".pdf", "application/pdf");
        testCases.put("", "unknown/unknown");

        for(val entry : testCases.entrySet())
        {
            MimeType mimeType = this.mime.fromFileName(entry.getKey());
            assertEquals(entry.getValue(), mimeType.Mime);
            if(!mimeType.equals(MimeType.unknown))
                assertNotNull(mimeType.MagicBytes);
            else
                assertNull(mimeType.MagicBytes);
        }
    }


    @Test
    public void test_that_ascii_is_not_detected_yet() throws IOException
    {
         byte[] bytes = FileUtils.readFileToByteArray(new File("src/test/resources/ImageSamples/is_a_text.txt"));

        val mimeType = this.mime.fromHeaderBytes(bytes);

        assertEquals(MimeType.unknown, mimeType);
    }

    @Test
    public void test_mime_detection_on_unknown_file() throws IOException
    {
        byte[] bytes = FileUtils.readFileToByteArray(new File("src/test/resources/ImageSamples/is_unknown.chm"));

        val mimeType = this.mime.fromHeaderBytes(bytes);

        assertEquals(MimeType.unknown, mimeType);
    }

}
