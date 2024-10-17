package com.smartestate.controller;

import com.smartestate.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@RestController
public class ImageController {
    private final ImageService imageService;

    @GetMapping("{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) {
        log.info("Received request for image with id: {}", id);

        Resource resource = imageService.getImageById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
