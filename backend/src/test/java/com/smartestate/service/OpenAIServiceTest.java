package com.smartestate.service;

import com.smartestate.dto.DescriptionDto;
import com.smartestate.dto.PropertyRequestDto;
import com.smartestate.model.enumeration.PropertyType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpenAIServiceTest {

    @Mock
    private ChatModel chatModel;

    @InjectMocks
    private OpenAIService openAIService;

    @Test
    void generateDescription_shouldReturnDescriptionDto() {
        // Arrange
        PropertyRequestDto propertyRequestDto = new PropertyRequestDto(
                PropertyType.APARTMENT, "Luxurious Apartment", null, "Poland",
                "Warsaw 25a", 2022, 5, 3, 4, 2, 2, new BigDecimal("150.0"),
                new BigDecimal("500000"), "USD"
        );
        String language = "en";

        String generatedText = "This luxurious apartment in Warsaw offers...";

        AssistantMessage message = mock(AssistantMessage.class);
        when(message.getContent()).thenReturn(generatedText);

        Generation generation = mock(Generation.class);
        when(generation.getOutput()).thenReturn(message);

        ChatResponse mockResponse = mock(ChatResponse.class);
        when(mockResponse.getResult()).thenReturn(generation);

        when(chatModel.call(any(Prompt.class))).thenReturn(mockResponse);

        // Act
        DescriptionDto result = openAIService.generateDescription(propertyRequestDto, language);

        // Assert
        assertNotNull(result);
        assertEquals(generatedText, result.description());
        verify(chatModel, times(1)).call(any(Prompt.class));
    }

    @Test
    void buildPrompt_shouldGenerateCorrectPrompt_englishInstruction() {
        // Arrange
        PropertyRequestDto propertyRequestDto = new PropertyRequestDto(
                PropertyType.APARTMENT, "Modern Apartment", null, "USA",
                "New York", 2020, 10, 5, 6, 3, 2, new BigDecimal("150.0"),
                new BigDecimal("1000000"), "USD"
        );
        String language = "en";

        // Act
        String prompt = openAIService.buildPrompt(propertyRequestDto, language);

        // Assert
        assertTrue(prompt.contains("Generate an encouraging and enticing description"));
        assertTrue(prompt.contains("Property Type: APARTMENT"));
        assertTrue(prompt.contains("Title: Modern Apartment"));
        assertTrue(prompt.contains("Please provide the description in English."));
    }

    @Test
    void buildPrompt_shouldGenerateCorrectPrompt_polishInstruction() {
        // Arrange
        PropertyRequestDto propertyRequestDto = new PropertyRequestDto(
                PropertyType.HOUSE, "Piękny dom", null, "Polska",
                "Kraków", 2015, 2, null, 5, 3, 2, new BigDecimal("200.0"),
                new BigDecimal("750000"), "PLN"
        );
        String language = "pl";

        // Act
        String prompt = openAIService.buildPrompt(propertyRequestDto, language);

        // Assert
        assertTrue(prompt.contains("Please provide the description in Polish."));
        assertTrue(prompt.contains("Property Type: HOUSE"));
        assertTrue(prompt.contains("Title: Piękny dom"));
        assertTrue(prompt.contains("Area: 200.0 m²"));
        assertTrue(prompt.contains("Price: 750000PLN"));
    }
}
