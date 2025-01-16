package com.smartestate.service;

import com.smartestate.exception.FileStorageException;
import com.smartestate.exception.ResourceNotFoundException;
import com.smartestate.model.Image;
import com.smartestate.model.Property;
import com.smartestate.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final PropertyService propertyService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Resource getImageResourceById(Long id) {
        log.info("Fetching image with id: {}", id);

        Image image = getImageById(id);
        String relativeFilePath = image.getFilePath();

        try {
            Path filePath = Paths.get(new ClassPathResource(relativeFilePath).getFile().getAbsolutePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                log.warn("Image file not found at path: {}", filePath);
                throw new ResourceNotFoundException(
                        String.format("Image not found at path: %s", filePath));
            }
            log.info("Image found for id: {}", id);
            return resource;
        } catch (IOException e) {
            log.error("Error accessing file directory", e);
            throw new FileStorageException("Error accessing file directory");
        }
    }

    public void saveImage(Long propertyId, MultipartFile file) {
        Property property = propertyService.getPropertyById(propertyId);

        String filePath = saveFileToStorage(file);

        Image image = Image.builder()
                .filePath(filePath)
                .property(property)
                .build();

        imageRepository.save(image);

        log.info("Image saved successfully for property ID: {}", propertyId);
    }

    private String saveFileToStorage(MultipartFile file) {
        try {
            String filename = UUID.randomUUID() + ".jpg";
            Path targetLocation = Paths.get(new ClassPathResource(uploadDir)
                    .getFile()
                    .getAbsolutePath()).resolve(filename);

            Files.createDirectories(targetLocation.getParent());

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return uploadDir + "/" + filename;
        } catch (IOException e) {
            log.error("Could not store file", e);
            throw new FileStorageException("Could not store file");
        }
    }

    private Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Image with id {} not found", id);
                    return new ResourceNotFoundException(
                            String.format("Image with id %d not found", id));
                });
    }

    public void deleteImage(Long imageId) {
        Image image = getImageById(imageId);

        try {
            Path filePath = Paths.get(new ClassPathResource(image.getFilePath())
                    .getFile()
                    .getAbsolutePath());
            Files.deleteIfExists(filePath);
            log.info("Deleted image file from path: {}", filePath);
        } catch (IOException e) {
            log.error("Failed to delete image file", e);
            throw new FileStorageException("Failed to delete image file");
        }

        imageRepository.deleteById(imageId);
        log.info("Deleted image record with id: {}", imageId);
    }
}
