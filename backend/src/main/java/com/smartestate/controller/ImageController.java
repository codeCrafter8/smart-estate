package com.smartestate.controller;

import com.smartestate.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@RestController
public class ImageController {
    private final ImageService imageService;

    @GetMapping("{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) {
        log.info("Received request for image with id: {}", id);

        Resource resource = imageService.getImageResourceById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @PostMapping("/upload/{propertyId}")
    public ResponseEntity<Void> uploadImage(
            @PathVariable("propertyId") Long propertyId,
            @RequestParam("files") List<MultipartFile> files) {
        log.info("Received request to upload image for property ID: {}", propertyId);

        files.forEach(file -> imageService.saveImage(propertyId, file));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        log.info("Received request to delete image with id: {}", imageId);

        imageService.deleteImageById(imageId);

        return ResponseEntity.noContent().build();
    }
}
