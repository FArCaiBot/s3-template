package com.farcai.awss3.service;

import org.springframework.web.multipart.MultipartFile;


public interface AWSS3Service {
    void uploadFile(MultipartFile file);
    byte[] downloadFile(String key);
}
