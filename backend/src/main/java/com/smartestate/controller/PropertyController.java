package com.smartestate.controller;

import com.smartestate.dto.PropertyDto;
import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.dto.PropertySearchCriteriaDto;
import com.smartestate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Long> addProperty(
            @RequestBody PropertyRequestDto propertyRequest,
            Principal principal) {
        log.info("Received request to add property: {}", propertyRequest);

        Long savedPropertyId = propertyService.addProperty(propertyRequest, principal);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPropertyId);
    }

    @GetMapping("/me")
    public ResponseEntity<List<PropertyDto>> getUserProperties(Principal principal) {
        log.info("Received request to fetch properties for user: {}", principal.getName());

        List<PropertyDto> userProperties = propertyService.getUserProperties(principal);

        return ResponseEntity.ok(userProperties);
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<PropertyDto> updateProperty(
            @PathVariable Long propertyId,
            @RequestBody PropertyRequestDto propertyRequestDto) {
        log.info("Received request to update property with id: {}", propertyId);

        PropertyDto updatedProperty = propertyService.updateProperty(propertyId, propertyRequestDto);

        return ResponseEntity.ok(updatedProperty);
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<PropertyDto> getPropertyById(@PathVariable Long propertyId) {
        log.info("Received request to fetch property with id: {}", propertyId);

        PropertyDto property = propertyService.getPropertyByIdDto(propertyId);

        return ResponseEntity.ok(property);
    }
}
