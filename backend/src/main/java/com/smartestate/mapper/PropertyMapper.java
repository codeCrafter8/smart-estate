package com.smartestate.mapper;

import com.smartestate.dto.PropertyDto;
import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.model.Image;
import com.smartestate.model.Property;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PropertyMapper {

    @Mapping(target = "country", source = "location.country")
    @Mapping(target = "address", source = "location.address")
    @Mapping(target = "imageIds", source = "images", qualifiedByName = "mapImagesToIds")
    PropertyDto toDto(Property property);

    @Named("mapImagesToIds")
    default List<Long> mapImagesToIds(List<Image> images) {
        return images.stream()
                .map(Image::getId)
                .toList();
    }

    Property toEntity(PropertyRequestDto propertyRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePropertyFromDto(PropertyRequestDto dto, @MappingTarget Property entity);
}
