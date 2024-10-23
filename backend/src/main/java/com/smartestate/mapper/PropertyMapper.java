package com.smartestate.mapper;

import com.smartestate.dto.PropertyDto;
import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.model.Image;
import com.smartestate.model.Property;
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

    public Property toEntity(PropertyRequestDto propertyRequest) {
        return Property.builder()
                .title(propertyRequest.title())
                .description(propertyRequest.description())
                .countryName(propertyRequest.countryName())
                .regionName(propertyRequest.regionName())
                .yearBuilt(propertyRequest.yearBuilt())
                .totalBuildingFloors(propertyRequest.totalBuildingFloors())
                .apartmentFloor(propertyRequest.apartmentFloor())
                .totalRooms(propertyRequest.totalRooms())
                .totalBedrooms(propertyRequest.totalBedrooms())
                .totalBathrooms(propertyRequest.totalBathrooms())
                .apartmentArea(propertyRequest.apartmentArea())
                .priceInUsd(propertyRequest.priceInUsd())
                .build();
    }
}
