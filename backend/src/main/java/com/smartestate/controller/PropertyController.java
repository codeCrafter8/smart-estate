package com.smartestate.controller;

import com.smartestate.dto.PropertySearchCriteriaDto;
import com.smartestate.model.Property;
import com.smartestate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/properties")
@RestController
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping("/search")
    public ResponseEntity<List<Property>> searchProperties(@RequestBody PropertySearchCriteriaDto criteria) {
        log.info("Received search request with criteria: {}", criteria);

        List<Property> properties = propertyService.searchProperties(criteria);

        log.info("Properties search completed.");

        return ResponseEntity.ok(properties);
    }
}
