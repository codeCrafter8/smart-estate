package com.smartestate.controller;

import com.smartestate.dto.PropertyInquiryRequest;
import com.smartestate.model.PropertyInquiry;
import com.smartestate.service.PropertyInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inquiries")
public class PropertyInquiryController {
    private final PropertyInquiryService propertyInquiryService;

    @PostMapping("/{propertyId}")
    public ResponseEntity<PropertyInquiry> submitInquiry(
            @PathVariable Long propertyId,
            @RequestBody PropertyInquiryRequest request) {
        PropertyInquiry inquiry = propertyInquiryService.addInquiry(propertyId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(inquiry);
    }

    @GetMapping("/{propertyId}")
    public List<PropertyInquiry> getInquiries(@PathVariable Long propertyId) {
        return propertyInquiryService.getInquiriesForProperty(propertyId);
    }
}
