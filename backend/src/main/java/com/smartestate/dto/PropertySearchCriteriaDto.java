package com.smartestate.dto;

import com.smartestate.model.enumeration.PropertyType;

import java.math.BigDecimal;

public record PropertySearchCriteriaDto(
    PropertyType propertyType,
    String location,
    BigDecimal minPrice,
    BigDecimal maxPrice,
    BigDecimal minArea,
    BigDecimal maxArea
) {
}


