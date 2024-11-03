package com.smartestate.dto;

import java.math.BigDecimal;
import java.util.List;

public record PropertyDto(
        Long id,
        String propertyType,
        String title,
        String description,
        String countryName,
        String regionName,
        Integer yearBuilt,
        Integer totalBuildingFloors,
        Integer apartmentFloor,
        Integer totalRooms,
        Integer totalBedrooms,
        Integer totalBathrooms,
        BigDecimal priceInUsd,
        BigDecimal apartmentArea,
        List<Long> imageIds
) {
}
