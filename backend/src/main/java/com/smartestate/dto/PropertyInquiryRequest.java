package com.smartestate.dto;

public record PropertyInquiryRequest(
        String phoneNumber,
        String email,
        String message
) {
}
