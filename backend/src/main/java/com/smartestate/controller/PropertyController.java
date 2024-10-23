package com.smartestate.controller;

import com.smartestate.dto.PropertyDto;
import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.dto.PropertySearchCriteriaDto;
import com.smartestate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/properties")
@RestController
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping("/search")
    public ResponseEntity<List<PropertyDto>> searchProperties(@RequestBody PropertySearchCriteriaDto criteria) {
        log.info("Received search request with criteria: {}", criteria);

        List<PropertyDto> properties = propertyService.searchProperties(criteria);

        log.info("Properties search completed.");

        return ResponseEntity.ok(properties);
    }

    @PostMapping
    public ResponseEntity<PropertyDto> addProperty(
            @RequestBody PropertyRequestDto propertyRequest,
            Principal principal) {
        log.info("Received request to add property: {}", propertyRequest);

        PropertyDto savedProperty = propertyService.addProperty(propertyRequest, principal);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProperty);
    }
}
