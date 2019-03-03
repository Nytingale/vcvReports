package com.vcv.backend.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {
    private String uploadDirectory;

    public String getUploadDirectory() {
        return uploadDirectory;
    }
    public void setUploadDirectory(String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }
}
