package com.smartestate.dto;

import java.math.BigDecimal;
import java.util.List;

public record PropertyDto(
        Long id,
        String propertyType,
        String title,
        String description,
        String country,
        String address,
        Integer yearBuilt,
        Integer totalBuildingFloors,
        Integer apartmentFloor,
        Integer totalRooms,
        Integer totalBedrooms,
        Integer totalBathrooms,
        BigDecimal price,
        String currency,
        BigDecimal area,
        List<Long> imageIds
) {
}
