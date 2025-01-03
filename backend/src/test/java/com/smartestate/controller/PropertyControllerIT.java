package com.smartestate.controller;

import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.model.enumeration.PropertyType;
import com.smartestate.repository.PropertyRepository;
import com.smartestate.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
class PropertyControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyService propertyService;

    private PropertyRequestDto propertyRequestDto;

    @BeforeEach
    void setUp() {
        propertyRequestDto = new PropertyRequestDto(
                PropertyType.APARTMENT,"New Property", "Description", "Czech Republic", "Prague",
                2023, 3, 2, 5, 3, 2,
                new BigDecimal("1500.0"), new BigDecimal("1000000.0"), "USD"
        );
    }

    @Test
    void testAddProperty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/properties")
                        .contentType("application/json")
                        .content("{\"title\":\"New Property\", \"description\":\"Description\", \"countryName\":\"Czech Republic\", \"regionName\":\"Prague\", \"yearBuilt\":2023, \"totalBuildingFloors\":3, \"apartmentFloor\":2, \"totalRooms\":5, \"totalBedrooms\":3, \"totalBathrooms\":2, \"apartmentArea\":1500.0, \"priceInUsd\":1000000.0}")
                        .principal(() -> "user1"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.toString()").exists());
    }

    /*@Test
    void testGetUserProperties() throws Exception {
        // Given
        Principal principal = () -> "user1";
        PropertyRequestDto propertyRequestDto = new PropertyRequestDto(
                "User Property", "Description", "Czech Republic", "Prague", 2023, 3, 2, 5, 3, 2, 2, 1500.0, 1000000.0
        );
        Long propertyId = propertyService.addProperty(propertyRequestDto, principal);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/properties/me")
                        .principal(principal))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(propertyId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("User Property"));
    }

    @Test
    void testSearchProperties() throws Exception {
        // Given
        PropertySearchCriteriaDto criteria = new PropertySearchCriteriaDto();
        PropertyRequestDto propertyRequestDto1 = new PropertyRequestDto("Property 1", "Description 1", "Czech Republic", "Prague", 2023, 3, 2, 5, 3, 2, 2, 1500.0, 1000000.0);
        PropertyRequestDto propertyRequestDto2 = new PropertyRequestDto("Property 2", "Description 2", "Czech Republic", "Brno", 2022, 2, 1, 4, 2, 1, 1, 1200.0, 800000.0);
        propertyService.addProperty(propertyRequestDto1, () -> "user1");
        propertyService.addProperty(propertyRequestDto2, () -> "user2");

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/properties/search")
                        .contentType("application/json")
                        .content("{\"countryName\":\"Czech Republic\", \"regionName\":\"Prague\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Property 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Property 2"));
    }

    @Test
    void testUpdateProperty() throws Exception {
        // Given
        PropertyRequestDto propertyRequestDto = new PropertyRequestDto(
                "Updated Property", "Updated Description", "Czech Republic", "Prague", 2023, 3, 2, 5, 3, 2, 2, 1500.0, 1200000.0
        );
        Long propertyId = propertyService.addProperty(propertyRequestDto, () -> "user1");

        PropertyRequestDto updatedRequest = new PropertyRequestDto(
                "Updated Property Name", "Updated Description", "Czech Republic", "Prague", 2023, 3, 2, 5, 3, 2, 2, 1500.0, 1300000.0
        );

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/properties/{propertyId}", propertyId)
                        .contentType("application/json")
                        .content("{\"title\":\"Updated Property Name\", \"description\":\"Updated Description\", \"countryName\":\"Czech Republic\", \"regionName\":\"Prague\", \"yearBuilt\":2023, \"totalBuildingFloors\":3, \"apartmentFloor\":2, \"totalRooms\":5, \"totalBedrooms\":3, \"totalBathrooms\":2, \"apartmentArea\":1500.0, \"priceInUsd\":1300000.0}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Property Name"));
    }

    @Test
    void testGetPropertyById() throws Exception {
        // Given
        PropertyRequestDto propertyRequestDto = new PropertyRequestDto(
                "Property Title", "Description", "Czech Republic", "Prague", 2023, 3, 2, 5, 3, 2, 2, 1500.0, 1000000.0
        );
        Long propertyId = propertyService.addProperty(propertyRequestDto, () -> "user1");

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/properties/{propertyId}", propertyId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(propertyId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Property Title"));
    }

    @Test
    void testDeleteProperty() throws Exception {
        // Given
        PropertyRequestDto propertyRequestDto = new PropertyRequestDto(
                "Property to Delete", "Description", "Czech Republic", "Prague", 2023, 3, 2, 5, 3, 2, 2, 1500.0, 1000000.0
        );
        Long propertyId = propertyService.addProperty(propertyRequestDto, () -> "user1");

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/properties/{propertyId}", propertyId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }*/
}
