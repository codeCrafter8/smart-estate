package com.smartestate.controller;

import com.smartestate.dto.AuthRequestDto;
import com.smartestate.dto.AuthResponseDto;
import com.smartestate.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody AuthRequestDto authRequestDto) {
        log.info("Sign-up request received for email: {}", authRequestDto.email());

        AuthResponseDto authResponseDto = authService.signUp(authRequestDto);
        log.info("Sign-up successful for email: {}", authRequestDto.email());

        return ResponseEntity.ok(authResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthRequestDto authRequestDto) {
        log.info("Authentication request received for email: {}", authRequestDto.email());

        AuthResponseDto authResponseDto = authService.authenticate(authRequestDto);
        log.info("Authentication successful for email: {}", authRequestDto.email());

        return ResponseEntity.ok(authResponseDto);
    }
}
