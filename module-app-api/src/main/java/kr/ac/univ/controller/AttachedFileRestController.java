package kr.ac.univ.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/attachedFiles")
public class AttachedFileRestController {
    @GetMapping("/download/{savedFileName}")
    public ResponseEntity<?> downloadAttachedFile(@PathVariable("savedFileName") String savedFileName) throws Exception {
        // 파일 이름이 한글인 경우 인코딩이 깨지지 않도록 변경
        String encordedSavedFileName = URLEncoder.encode(savedFileName, "UTF-8").replace("+", "%20");

        // 헤더 추가
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + encordedSavedFileName.substring(33));
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        // MimeType 추가, application/octet-stream은 text/plain 타입을 제외한 기본 값
        MediaType mediaType = MediaType.parseMediaType("application/octet-stream");

        // 다운로드 파일 추가
        Path path = Paths.get("./upload/" + savedFileName);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentType(mediaType)
                .body(resource);
    }
}