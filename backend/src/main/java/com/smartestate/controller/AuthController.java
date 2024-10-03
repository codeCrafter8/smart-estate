package com.smartestate.controller;

import com.smartestate.dto.AuthRequestDto;
import com.smartestate.dto.AuthResponseDto;
import com.smartestate.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto authResponseDto = authService.signUp(authRequestDto);

        return ResponseEntity.ok(authResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto authResponseDto = authService.authenticate(authRequestDto);

        return ResponseEntity.ok(authResponseDto);
    }
}
