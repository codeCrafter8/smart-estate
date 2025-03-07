package com.smartestate.service;

import com.smartestate.dto.PropertyDto;
import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.dto.PropertySearchCriteriaDto;
import com.smartestate.exception.ResourceNotFoundException;
import com.smartestate.mapper.LocationMapper;
import com.smartestate.mapper.PriceMapper;
import com.smartestate.mapper.PropertyMapper;
import com.smartestate.model.Location;
import com.smartestate.model.Price;
import com.smartestate.model.Property;
import com.smartestate.model.User;
import com.smartestate.repository.PropertyRepository;
import com.smartestate.repository.PropertySpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final UserService userService;
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final PriceService priceService;
    private final PriceMapper priceMapper;

    public List<PropertyDto> searchProperties(PropertySearchCriteriaDto criteria) {
        log.info("Searching properties with criteria: {}", criteria);

        Specification<Property> spec = PropertySpecification.filterByCriteria(criteria);
        List<Property> properties = propertyRepository.findAll(spec);

        log.info("Found {} properties", properties.size());

        return properties.stream()
                .map(propertyMapper::toDto)
                .toList();
    }

    @Transactional
    public Long addProperty(PropertyRequestDto propertyRequest, Principal principal) {
        String userEmail = principal.getName();
        log.info("Adding property for user: {}", userEmail);

        User user = userService.findByEmail(userEmail);

        Property property = propertyMapper.toEntity(propertyRequest);
        property.setUser(user);

        Location location = locationService.addLocation(propertyRequest.address(), propertyRequest.country());
        property.setLocation(location);

        Price price = priceService.addPrice(propertyRequest.priceAmount(), propertyRequest.currency());
        property.setPrice(price);

        Property savedProperty = propertyRepository.save(property);

        log.info("Property added successfully: {}", savedProperty);

        return savedProperty.getId();
    }

    public Property getPropertyById(Long id) {
        log.info("Fetching property with id: {}", id);

        return propertyRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Property with id {} not found", id);
                    return new ResourceNotFoundException(
                            String.format("Property with id %d not found", id));
                });
    }

    public List<PropertyDto> getUserProperties(Principal principal) {
        String userEmail = principal.getName();
        log.info("Fetching properties for user: {}", userEmail);

        User user = userService.findByEmail(userEmail);

        List<Property> userProperties = propertyRepository.findByUserId(user.getId());

        return userProperties.stream()
                .map(propertyMapper::toDto)
                .toList();
    }

    public PropertyDto updateProperty(Long propertyId, PropertyRequestDto propertyRequest) {
        Property existingProperty = getPropertyById(propertyId);

        propertyMapper.updatePropertyFromDto(propertyRequest, existingProperty);
        locationMapper.updateLocationFromDto(propertyRequest, existingProperty.getLocation());
        priceMapper.updatePriceFromDto(propertyRequest, existingProperty.getPrice());

        Property updatedProperty = propertyRepository.save(existingProperty);

        log.info("Property updated successfully: {}", updatedProperty);

        return propertyMapper.toDto(updatedProperty);
    }

    public PropertyDto getPropertyDtoById(Long propertyId) {
        Property property = getPropertyById(propertyId);
        return propertyMapper.toDto(property);
    }

    public void deleteProperty(Long propertyId) {
        Property property = getPropertyById(propertyId);

        propertyRepository.delete(property);

        log.info("Property with id {} deleted successfully", propertyId);
    }
}
