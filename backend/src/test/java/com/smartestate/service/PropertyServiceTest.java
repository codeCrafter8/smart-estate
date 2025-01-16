package com.smartestate.service;

import com.smartestate.dto.PropertyDto;
import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.dto.PropertySearchCriteriaDto;
import com.smartestate.exception.ResourceNotFoundException;
import com.smartestate.mapper.LocationMapper;
import com.smartestate.mapper.PriceMapper;
import com.smartestate.mapper.PropertyMapper;
import com.smartestate.model.Location;
import com.smartestate.model.Price;
import com.smartestate.model.Property;
import com.smartestate.model.User;
import com.smartestate.repository.PropertyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private PropertyMapper propertyMapper;

    @Mock
    private UserService userService;

    @Mock
    private LocationService locationService;

    @Mock
    private LocationMapper locationMapper;

    @Mock
    private PriceService priceService;

    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private PropertyService propertyService;

    @Test
    void searchProperties_shouldReturnListOfMappedDto() {
        PropertySearchCriteriaDto criteria = new PropertySearchCriteriaDto(
                null, "Test Location", BigDecimal.valueOf(100000), BigDecimal.valueOf(300000),
                BigDecimal.valueOf(50), BigDecimal.valueOf(150), "USD"
        );
        Property property = mock(Property.class);
        PropertyDto propertyDto = mock(PropertyDto.class);

        when(propertyRepository.findAll(any(Specification.class))).thenReturn(List.of(property));
        when(propertyMapper.toDto(property)).thenReturn(propertyDto);

        List<PropertyDto> result = propertyService.searchProperties(criteria);

        assertEquals(1, result.size());
        assertSame(propertyDto, result.getFirst());
        verify(propertyRepository).findAll(any(Specification.class));
        verify(propertyMapper).toDto(property);
    }

    @Test
    void addProperty_shouldSaveProperty() {
        PropertyRequestDto request = mock(PropertyRequestDto.class);
        Principal principal = mock(Principal.class);
        User user = mock(User.class);
        Location location = mock(Location.class);
        Price price = mock(Price.class);
        Property property = mock(Property.class);
        Property savedProperty = mock(Property.class);

        when(principal.getName()).thenReturn("user@example.com");
        when(userService.findByEmail("user@example.com")).thenReturn(user);
        when(propertyMapper.toEntity(request)).thenReturn(property);
        when(locationService.addLocation(any(), any())).thenReturn(location);
        when(priceService.addPrice(any(), any())).thenReturn(price);
        when(propertyRepository.save(property)).thenReturn(savedProperty);
        when(savedProperty.getId()).thenReturn(1L);

        Long result = propertyService.addProperty(request, principal);

        assertEquals(1L, result);
        verify(propertyRepository).save(property);
        verify(propertyMapper).toEntity(request);
        verify(locationService).addLocation(any(), any());
        verify(priceService).addPrice(any(), any());
    }

    @Test
    void getPropertyById_shouldReturnPropertyIfExists() {
        Property property = mock(Property.class);
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        Property result = propertyService.getPropertyById(1L);

        assertSame(property, result);
        verify(propertyRepository).findById(1L);
    }

    @Test
    void getPropertyById_shouldThrowExceptionIfNotFound() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> propertyService.getPropertyById(1L));
        verify(propertyRepository).findById(1L);
    }

    @Test
    void getUserProperties_shouldReturnUserProperties() {
        Principal principal = mock(Principal.class);
        User user = mock(User.class);
        Property property = mock(Property.class);
        PropertyDto propertyDto = mock(PropertyDto.class);

        when(principal.getName()).thenReturn("user@example.com");
        when(userService.findByEmail("user@example.com")).thenReturn(user);
        when(user.getId()).thenReturn(1L);
        when(propertyRepository.findByUserId(1L)).thenReturn(List.of(property));
        when(propertyMapper.toDto(property)).thenReturn(propertyDto);

        List<PropertyDto> result = propertyService.getUserProperties(principal);

        assertEquals(1, result.size());
        assertSame(propertyDto, result.getFirst());
    }

    @Test
    void updateProperty_shouldUpdateAndReturnDto() {
        PropertyRequestDto request = mock(PropertyRequestDto.class);
        Property property = mock(Property.class);
        Location location = mock(Location.class);
        Price price = mock(Price.class);
        PropertyDto propertyDto = mock(PropertyDto.class);

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(property.getLocation()).thenReturn(location);
        when(property.getPrice()).thenReturn(price);
        when(propertyRepository.save(property)).thenReturn(property);
        when(propertyMapper.toDto(property)).thenReturn(propertyDto);

        PropertyDto result = propertyService.updateProperty(1L, request);

        assertSame(propertyDto, result);
        verify(propertyMapper).updatePropertyFromDto(request, property);
        verify(locationMapper).updateLocationFromDto(request, location);
        verify(priceMapper).updatePriceFromDto(request, price);
    }

    @Test
    void deleteProperty_shouldDeleteIfExists() {
        Property property = mock(Property.class);

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        propertyService.deleteProperty(1L);

        verify(propertyRepository).delete(property);
    }
}
