package com.smartestate.service;

import com.smartestate.dto.PropertyDto;
import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.dto.PropertySearchCriteriaDto;
import com.smartestate.mapper.PropertyMapper;
import com.smartestate.model.Property;
import com.smartestate.model.User;
import com.smartestate.repository.PropertyRepository;
import com.smartestate.repository.PropertySpecification;
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

    public List<PropertyDto> searchProperties(PropertySearchCriteriaDto criteria) {
        log.info("Searching properties with criteria: {}", criteria);

        Specification<Property> spec = PropertySpecification.filterByCriteria(criteria);
        List<Property> properties = propertyRepository.findAll(spec);

        log.info("Found {} properties", properties.size());

        return properties.stream()
                .map(propertyMapper::toDto)
                .toList();
    }

    public PropertyDto addProperty(PropertyRequestDto propertyRequest, Principal principal) {
        String userEmail = principal.getName();
        log.info("Adding property for user: {}", userEmail);

        User user = userService.findByEmail(userEmail);

        Property property = propertyMapper.toEntity(propertyRequest);
        property.setUser(user);

        Property savedProperty = propertyRepository.save(property);

        log.info("Property added successfully: {}", savedProperty);

        return propertyMapper.toDto(savedProperty);
    }
}
