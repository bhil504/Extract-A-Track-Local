package com.BhillionDollarApps.extrack_a_track.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class S3FileUploader {

    private static final Logger logger = Logger.getLogger(S3FileUploader.class.getName());

    @Autowired
    private S3Client s3Client;

    // Uploads a file to S3 bucket at a specified key path
    public void uploadFile(String bucketName, String s3Key, String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new IOException("File does not exist: " + filePath);
            }

            // Prepare the PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            // Upload the file
            s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));
            logger.info("File uploaded successfully to S3: " + s3Key);
        } catch (IOException e) {
            logger.severe("Error uploading file to S3: " + e.getMessage());
            throw new RuntimeException("Failed to upload file to S3.", e);
        }
    }

    // Uploads file bytes directly to S3
    public void uploadFileBytes(String bucketName, String s3Key, byte[] fileBytes) {
        try {
            // Prepare the PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            // Upload the bytes directly
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileBytes));
            logger.info("File bytes uploaded successfully to S3: " + s3Key);
        } catch (Exception e) {
            logger.severe("Error uploading file bytes to S3: " + e.getMessage());
            throw new RuntimeException("Failed to upload file bytes to S3.", e);
        }
    }

    // Deletes a file from S3
    public void deleteFile(String bucketName, String s3Key) {
        try {
            // Prepare the delete request
            s3Client.deleteObject(delete -> delete.bucket(bucketName).key(s3Key));
            logger.info("File deleted successfully from S3: " + s3Key);
        } catch (Exception e) {
            logger.severe("Error deleting file from S3: " + e.getMessage());
            throw new RuntimeException("Failed to delete file from S3.", e);
        }
    }
    
    // Uploads a MultipartFile to S3 at the specified key path
    public String uploadFile(MultipartFile file, String bucketName, String s3Key) {
        try {
            // Prepare the PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            // Upload the file content
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            logger.info("File uploaded successfully to S3: " + s3Key);
            return s3Key;
        } catch (IOException e) {
            logger.severe("Error uploading file to S3: " + e.getMessage());
            throw new RuntimeException("Failed to upload file to S3.", e);
        }
    }
}
    

