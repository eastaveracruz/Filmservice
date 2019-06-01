package filmservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Random;

public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public static File uploadFile(File direcotry, MultipartFile file, String name) throws Exception {
        File uploadedFile = null;

            if (!file.isEmpty()) {
                if (!direcotry.exists()) {
                    direcotry.mkdirs();
                }
                uploadedFile =  new File(direcotry.getAbsolutePath() + File.separator + name);
                Files.write(uploadedFile.toPath(), file.getBytes(), StandardOpenOption.CREATE);
            } else {
                throw new Exception("Downloaded file is empty.");
            }
        return uploadedFile;
    }

}
