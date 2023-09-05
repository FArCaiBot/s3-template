package com.farcai.awss3.controller;

import com.farcai.awss3.service.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("s3")
@CrossOrigin("*")
public class UploadFileController {

    @Autowired
    private AWSS3Service awss3Service;

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile file) {
        awss3Service.uploadFile(file);
        String response = "El archivo " + file.getOriginalFilename() + " fue cargado correctamente";
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/download")
    public ResponseEntity<ByteArrayResource> download(@RequestParam("key") String key) {
        byte[] data = awss3Service.downloadFile(key);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-type", "application/octect-stream")
                .header("Content-disposition", "attachment; filename=\"" + key + "\"")
                .body(resource);
    }
}
