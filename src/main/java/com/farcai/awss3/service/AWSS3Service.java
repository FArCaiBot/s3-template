package com.farcai.awss3.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface AWSS3Service {
    void uploadFile(MultipartFile file);
    byte[] downloadFile(String key);
}
