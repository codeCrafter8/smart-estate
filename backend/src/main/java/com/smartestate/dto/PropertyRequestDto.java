package com.smartestate.dto;

import com.smartestate.model.Image;
import com.smartestate.model.enumeration.PropertyType;

import java.math.BigDecimal;
import java.util.List;

public record PropertyRequestDto(
        PropertyType propertyType,
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
        BigDecimal apartmentArea,
        BigDecimal priceInUsd,
        List<Image> images
) {}
