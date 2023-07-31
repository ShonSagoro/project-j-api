package com.estancia.juventudes.controllers;

import com.estancia.juventudes.controllers.dtos.response.BaseResponse;
import com.estancia.juventudes.services.GoogleDriveServiceImpl;
import com.estancia.juventudes.services.interfaces.IGoogleDriveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private IGoogleDriveService service;

    @Value("${upload.path}")
    private String uploadPath;

    @Operation(summary = "Upload a file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The file has been uploaded",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "File not valid",
                    content = @Content)
    })
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


    @Operation(summary = "Download a file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The file has been found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEntity.class)) })
    })
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        Path filePath = Paths.get(uploadPath + fileName);
        byte[] fileContent = Files.readAllBytes(filePath);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.IMAGE_JPEG);

        return ResponseEntity.ok().headers(responseHeaders).body(fileContent);
    }



    @PostMapping(value = "/upload/drive",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> uploadSingleFile(@RequestBody MultipartFile[] files,@RequestParam(required = false) String path) {
        int filesize = files.length;
        AtomicReference<String> fileId = new AtomicReference<>("");
        AtomicReference<String> fileName = new AtomicReference<>("");
        Arrays.asList(files).forEach(
                file->{
                    fileId.set(service.uploadFile(file, path));
                    fileName.set(file.getOriginalFilename());
                }
        );

        if (filesize > 1){
            return ResponseEntity.ok("files uploaded successfully");
        }
        return ResponseEntity.ok(fileName + ", uploaded successfully");
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<BaseResponse> download(@PathVariable String id, HttpServletResponse response) throws IOException, GeneralSecurityException {
        return service.downloadFile(id, response.getOutputStream()).apply();
    }


    @GetMapping("/list")
    public ResponseEntity<BaseResponse> listEverything() throws IOException, GeneralSecurityException {
        return service.listEverything().apply();
    }


    @Operation(summary = " Quick check of controller operation")
    @GetMapping("health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
