package com.smartestate.mapper;

import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.model.Price;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Mapping(source = "priceAmount", target = "amount")
    void updatePriceFromDto(PropertyRequestDto propertyRequest, @MappingTarget Price price);

}
