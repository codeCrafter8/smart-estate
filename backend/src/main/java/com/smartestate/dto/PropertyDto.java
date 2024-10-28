package com.smartestate.dto;

import java.math.BigDecimal;
import java.util.List;

public record PropertyDto(
        Long id,
        String title,
        String description,
        String countryName,
        String regionName,
        BigDecimal priceInUsd,
        BigDecimal apartmentArea,
        List<Long> imageIds
) {
}
