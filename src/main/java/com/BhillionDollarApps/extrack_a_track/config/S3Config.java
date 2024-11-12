package com.BhillionDollarApps.extrack_a_track.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_1) // Replace with your actual region
                .credentialsProvider(DefaultCredentialsProvider.create()) // Uses default AWS credentials
                .build();
    }
}