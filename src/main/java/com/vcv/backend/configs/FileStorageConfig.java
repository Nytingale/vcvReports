package com.vcv.backend.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "file")
@Configuration("fileStorageProperties")
public class FileStorageConfig {
    private String imagesDir;

    public String getImagesDir() {
        return imagesDir;
    }
    public void setImagesDir(String imagesDir) {
        this.imagesDir = imagesDir;
    }
}
