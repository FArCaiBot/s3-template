package com.farcai.awss3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class AWSS3ServiceImpl implements AWSS3Service {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public void uploadFile(MultipartFile file) {
        File mainFile = new File(file.getOriginalFilename());
        try (FileOutputStream stream = new FileOutputStream(mainFile)) {
            stream.write(file.getBytes());
            String newFileName = System.currentTimeMillis() + "_" + mainFile.getName();
            PutObjectRequest request = new PutObjectRequest(bucketName, newFileName, mainFile);
            amazonS3.putObject(request);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mainFile.delete();
        }
    }

    @Override
    public byte[] downloadFile(String key) {
        S3Object object = amazonS3.getObject(bucketName, key);
        S3ObjectInputStream inputStream = object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
