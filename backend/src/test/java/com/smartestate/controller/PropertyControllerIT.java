package com.smartestate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.dto.PropertySearchCriteriaDto;
import com.smartestate.model.enumeration.PropertyType;
import com.smartestate.repository.ImageRepository;
import com.smartestate.repository.PropertyRepository;
import com.smartestate.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.security.Principal;

import static org.hamcrest.Matchers.matchesPattern;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
class PropertyControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ImageRepository imageRepository;

    @BeforeEach
    void setUp() {
        imageRepository.deleteAll();
        propertyRepository.deleteAll();
    }

    @Test
    void testSearchProperties() throws Exception {
        // Given
        PropertySearchCriteriaDto criteria = new PropertySearchCriteriaDto(null, null, new BigDecimal("150000.0"), new BigDecimal("200000.0"), null, null, "USD");
        PropertyRequestDto propertyRequestDto1 = createPropertyRequest("Property 1", new BigDecimal("160000.0"));
        PropertyRequestDto propertyRequestDto2 = createPropertyRequest("Property 2", new BigDecimal("185000.0"));
        PropertyRequestDto propertyRequestDto3 = createPropertyRequest("Property 3", new BigDecimal("125000.0"));
        propertyService.addProperty(propertyRequestDto1, () -> "john.doe@example.com");
        propertyService.addProperty(propertyRequestDto2, () -> "jane.smith@example.com");
        propertyService.addProperty(propertyRequestDto3, () -> "jane.smith@example.com");

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/properties/search")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(criteria)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Property 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Property 2"));
    }

    @WithMockUser("jane.smith@example.com")
    @Test
    void testAddProperty() throws Exception {
        PropertyRequestDto propertyRequestDto = createPropertyRequest("New Property", new BigDecimal("1000000.0"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/properties")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(propertyRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(matchesPattern("\\d+")));
    }

    @WithMockUser("jane.smith@example.com")
    @Test
    void testGetUserProperties() throws Exception {
        // Given
        PropertyRequestDto propertyRequestDto = createPropertyRequest("New Property", new BigDecimal("1000000.0"));
        Principal principal = () -> "jane.smith@example.com";
        Long propertyId = propertyService.addProperty(propertyRequestDto, principal);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/properties/me")
                        .principal(principal))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(propertyId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("New Property"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].priceAmount").value(1000000.0));
    }

    private PropertyRequestDto createPropertyRequest(String title, BigDecimal price) {
        return new PropertyRequestDto(
                PropertyType.APARTMENT, title, "Description", "Czech Republic", "Prague",
                2023, 3, 2, 5, 3, 2,
                new BigDecimal("1500.0"), price, "USD"
        );
    }

    /*@Test
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
