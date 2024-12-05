package com.smartestate.mapper;

import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.model.Location;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toEntity(PropertyRequestDto propertyRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLocationFromDto(PropertyRequestDto propertyRequest, @MappingTarget Location location);
}
