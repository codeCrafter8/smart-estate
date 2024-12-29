package com.smartestate.mapper;

import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.model.Location;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    void updateLocationFromDto(PropertyRequestDto propertyRequest, @MappingTarget Location location);

}
