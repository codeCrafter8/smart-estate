package com.smartestate.service;

import com.smartestate.dto.PropertyInquiryRequest;
import com.smartestate.model.Property;
import com.smartestate.model.PropertyInquiry;
import com.smartestate.model.enumeration.InquiryStatus;
import com.smartestate.repository.PropertyInquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PropertyInquiryService {
    private final PropertyInquiryRepository propertyInquiryRepository;
    private final PropertyService propertyService;

    public PropertyInquiry addInquiry(Long propertyId, PropertyInquiryRequest request) {
        Property property = propertyService.getPropertyById(propertyId);

        PropertyInquiry inquiry = PropertyInquiry.builder()
                .property(property)
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .status(InquiryStatus.PENDING)
                .build();

        return propertyInquiryRepository.save(inquiry);
    }

    public List<PropertyInquiry> getInquiriesForProperty(Long propertyId) {
        return propertyInquiryRepository.findByPropertyId(propertyId);
    }
}

