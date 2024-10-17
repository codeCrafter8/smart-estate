package com.smartestate.service;

import com.smartestate.exception.FileStorageException;
import com.smartestate.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public Resource getImageById(Long id) {
        try {
            log.info("Fetching image with id: {}", id);
            String filename = getFileNameById(id);
            Path filePath = Paths.get(new ClassPathResource(uploadDir).getFile().getAbsolutePath()).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                log.info("Image found for id: {}", id);
                return resource;
            } else {
                log.warn("Image not found for id: {}", id);
                throw new ResourceNotFoundException("Image not found for id: " + id);
            }
        } catch (IOException e) {
            log.error("Error accessing file directory", e);
            throw new FileStorageException("Error accessing file directory");
        }
    }

    private String getFileNameById(Long id) {
        return id + ".jpg";
    }
}
