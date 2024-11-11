package com.smartestate.dto;

import com.smartestate.model.enumeration.PropertyType;

import java.math.BigDecimal;

public record PropertyRequestDto(
        PropertyType propertyType,
        String title,
        String description,
        String countryName,
        String locationName,
        Integer yearBuilt,
        Integer totalBuildingFloors,
        Integer apartmentFloor,
        Integer totalRooms,
        Integer totalBedrooms,
        Integer totalBathrooms,
        BigDecimal apartmentArea,
        BigDecimal priceInUsd
) {}
