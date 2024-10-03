package com.smartestate.dto;

public record AuthRequestDto(
        String email,
        String password
) {
}
