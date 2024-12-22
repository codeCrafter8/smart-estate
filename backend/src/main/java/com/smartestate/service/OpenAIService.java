package com.smartestate.service;

import com.smartestate.dto.DescriptionDto;
import com.smartestate.dto.PropertyRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OpenAIService {
    private final ChatModel chatModel;

    public DescriptionDto generateDescription(PropertyRequestDto propertyRequestDto, String language) {
        String promptText = buildPrompt(propertyRequestDto, language);
        log.info("Generated prompt for OpenAI: {}", promptText);

        ChatResponse response = chatModel.call(
                new Prompt(
                        promptText,
                        OpenAiChatOptions.builder()
                                .withMaxTokens(300)
                                .withTemperature(0.7)
                                .build()
                )
        );

        String result = response.getResult().getOutput().getContent();
        log.info("Received description from OpenAI: {}", result);

        return new DescriptionDto(result);
    }

    private String buildPrompt(PropertyRequestDto propertyRequestDto, String language) {
        StringBuilder promptBuilder = new StringBuilder("Generate an encouraging and enticing description for a real estate listing based on the following details. Please provide a plain text without any special formatting or symbols.");

        String languageInstruction = "pl".equals(language)
                ? "Please provide the description in Polish."
                : "Please provide the description in English.";

        promptBuilder.append(languageInstruction).append(" ");

        appendIfPresent(promptBuilder, "Property Type", propertyRequestDto.propertyType());
        appendIfPresent(promptBuilder, "Title", propertyRequestDto.title());
        appendIfPresent(promptBuilder, "Country", propertyRequestDto.country());
        appendIfPresent(promptBuilder, "Location", propertyRequestDto.address());
        appendIfPresent(promptBuilder, "Year Built", propertyRequestDto.yearBuilt());
        appendIfPresent(promptBuilder, "Total Building Floors", propertyRequestDto.totalBuildingFloors());
        appendIfPresent(promptBuilder, "Apartment Floor", propertyRequestDto.apartmentFloor());
        appendIfPresent(promptBuilder, "Total Rooms", propertyRequestDto.totalRooms());
        appendIfPresent(promptBuilder, "Bedrooms", propertyRequestDto.totalBedrooms());
        appendIfPresent(promptBuilder, "Bathrooms", propertyRequestDto.totalBathrooms());
        appendIfPresent(promptBuilder, "Area", propertyRequestDto.area() + " m²");
        appendIfPresent(promptBuilder, "Price", propertyRequestDto.priceAmount() + propertyRequestDto.currency());

        if (promptBuilder.charAt(promptBuilder.length() - 1) == ',') {
            promptBuilder.setLength(promptBuilder.length() - 1);
        }

        promptBuilder.append(".");

        return promptBuilder.toString();
    }

    private void appendIfPresent(StringBuilder builder, String label, Object value) {
        Optional.ofNullable(value)
                .filter(v -> !(v instanceof String string && string.isEmpty()))
                .ifPresent(v -> builder.append(" ")
                        .append(label)
                        .append(": ")
                        .append(v)
                        .append(","));
    }
}
