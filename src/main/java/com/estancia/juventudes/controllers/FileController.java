package com.estancia.juventudes.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
public class FileController {

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Archivo no válido", HttpStatus.BAD_REQUEST);
        }
        String fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + "." + file.getOriginalFilename().split("\\.")[1]);

        if (!Files.exists(Paths.get(uploadPath))) {
            Files.createDirectories(Paths.get(uploadPath));
        }

        Path filePath = Paths.get(uploadPath + fileName);
        Files.write(filePath, file.getBytes());

        return ResponseEntity.ok("http://localhost:8080/files/" + fileName);
    }


    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        Path filePath = Paths.get(uploadPath + fileName);
        byte[] fileContent = Files.readAllBytes(filePath);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.IMAGE_JPEG);

        return ResponseEntity.ok().headers(responseHeaders).body(fileContent);
    }
}
