package com.smartestate.controller;

import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/openai")
@RestController
public class OpenAIController {
    private final OpenAIService openAIService;

    @PostMapping("/generate-description")
    public ResponseEntity<String> generateDescription(@RequestBody PropertyRequestDto propertyRequestDto) {
        log.info("Received request to generate description for property: {}", propertyRequestDto);

        String description = openAIService.generateDescription(propertyRequestDto);

        log.info("Description generation completed.");

        return ResponseEntity.ok(description);
    }
}
