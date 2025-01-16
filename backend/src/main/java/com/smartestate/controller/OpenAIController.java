package com.smartestate.controller;

import com.smartestate.dto.DescriptionDto;
import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/openai")
@RestController
public class OpenAIController {
    private final OpenAIService openAIService;

    @PostMapping("/generate-description")
    public ResponseEntity<DescriptionDto> generateDescription(
            @RequestBody PropertyRequestDto propertyRequestDto,
            @RequestParam(defaultValue = "en") String language) {
        log.info("Received request to generate description for property: {} with language: {}", propertyRequestDto, language);

        DescriptionDto description = openAIService.generateDescription(propertyRequestDto, language);

        log.info("Description generation completed.");

        return ResponseEntity.ok(description);
    }
}
