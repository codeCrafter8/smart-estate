package com.smartestate.service;

import com.smartestate.dto.PropertyDto;
import com.smartestate.dto.PropertySearchCriteriaDto;
import com.smartestate.mapper.PropertyMapper;
import com.smartestate.model.Property;
import com.smartestate.repository.PropertyRepository;
import com.smartestate.repository.PropertySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;

    public List<PropertyDto> searchProperties(PropertySearchCriteriaDto criteria) {
        log.info("Searching properties with criteria: {}", criteria);

        Specification<Property> spec = PropertySpecification.filterByCriteria(criteria);
        List<Property> properties = propertyRepository.findAll(spec);

        log.info("Found {} properties", properties.size());

        return properties.stream()
                .map(propertyMapper::toDto)
                .toList();
    }
}
