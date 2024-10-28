package com.smartestate.service;

import com.smartestate.dto.AuthRequestDto;
import com.smartestate.dto.AuthResponseDto;
import com.smartestate.exception.DuplicateResourceException;
import com.smartestate.model.User;
import com.smartestate.repository.UserRepository;
import com.smartestate.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponseDto signUp(AuthRequestDto authRequestDto) {
        log.info("Attempting to sign up user with email: {}", authRequestDto.email());

        if (userRepository.existsByEmail(authRequestDto.email())) {
            log.warn("Sign-up failed: User with email {} already exists.", authRequestDto.email());
            throw new DuplicateResourceException("User with email " + authRequestDto.email() + " already exists.");
        }

        User user = User.builder()
                .email(authRequestDto.email())
                .password(passwordEncoder.encode(authRequestDto.password()))
                .build();

        userRepository.save(user);
        log.info("User with email {} saved to the database.", authRequestDto.email());

        String jwtToken = jwtUtil.generateToken(user);
        log.info("JWT Token generated for user {}: {}", authRequestDto.email(), jwtToken);

        return new AuthResponseDto(jwtToken);
    }

    public AuthResponseDto authenticate(AuthRequestDto authRequestDto) {
        log.info("Authenticating user with email: {}", authRequestDto.email());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.email(),
                        authRequestDto.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtil.generateToken(userDetails);
        log.info("User with email {} authenticated successfully.", authRequestDto.email());
        log.info("JWT Token generated for authenticated user {}: {}", authRequestDto.email(), jwtToken);

        return new AuthResponseDto(jwtToken);
    }
}
