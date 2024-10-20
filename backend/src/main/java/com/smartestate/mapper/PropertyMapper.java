package com.smartestate.mapper;

import com.smartestate.dto.PropertyDto;
import com.smartestate.model.Property;
import com.smartestate.model.Image;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PropertyMapper {
    public PropertyDto toDto(Property property) {
        List<Long> imageIds = property.getImages().stream()
                .map(Image::getId)
                .toList();

        return new PropertyDto(
                property.getId(),
                property.getTitle(),
                property.getDescription(),
                property.getCountryName(),
                property.getRegionName(),
                property.getPriceInUsd(),
                property.getApartmentArea(),
                imageIds
        );
    }
}
