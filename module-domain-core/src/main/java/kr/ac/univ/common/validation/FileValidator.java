package kr.ac.univ.common.validation;

import java.io.IOException;

import kr.ac.univ.util.FileUtil;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileValidator {
    /**
     * file type이 유효한지 검사
     *
     * @param files
     * @return
     * @throws IOException
     */
    public static String isFileValid(MultipartFile[] files) throws IOException {
        Tika tika = new Tika();
        String result = "valid";

        for (MultipartFile file : files) {
            String mimeType = tika.detect(file.getBytes());
            String extension = FileUtil.getExtension(file.getOriginalFilename());

            if (FileType.invalidMimeTypeSet.contains(mimeType)) {
                log.info(mimeType + ", " + extension);
                result = "The file " + file.getOriginalFilename() + " [mime type: " + mimeType + "] doesn't support to upload because it supposed to dangerous and malicious.";
                break;
            }

            if (FileType.invalidExtensionSet.contains(extension)) {
                log.info(mimeType + ", " + extension);
                result = "The file " + file.getOriginalFilename() + " [extension: " + extension + "] doesn't support to upload because it supposed to dangerous and malicious.";
                break;
            }
        }

        return result;
    }

    public static String isImageFileValid(MultipartFile[] files) throws IOException {
        Tika tika = new Tika();
        String result = "valid";

        for (MultipartFile file : files) {
            String mimeType = tika.detect(file.getBytes());
            String extension = FileUtil.getExtension(file.getOriginalFilename());

            log.info(mimeType + ", " + extension);

            if (!FileType.validImageTypeSet.contains(mimeType)) {
                result = "The file " + file.getOriginalFilename() + "[mimet ype: " + mimeType + "] doesn't support to upload because it supposed to not image type.";
                break;
            }
        }

        return result;
    }

}