package kr.ac.univ.setting.service;

import kr.ac.univ.seminar.domain.SeminarAttachedFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SettingAttachedFileService {
    /**
     * 로고 파일 업로드
     *
     * @param files
     * @throws Exception
     */
    public void uploadAttachedFile(MultipartFile[] files) throws Exception {
        SeminarAttachedFile uploadFile;

        for (MultipartFile file : files) {
            String savedFileName = "logo.png";

            // 대체 가능
            // File savedFile = new File("./upload/", savedFileName);
            // FileCopyUtils.copy(file.getBytes(), savedFile);
            Path path = Paths.get("./upload/" + savedFileName);
            Files.write(path, file.getBytes());
        }
    }
}