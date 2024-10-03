package com.smartestate.service;

import com.smartestate.dto.AuthRequestDto;
import com.smartestate.dto.AuthResponseDto;
import com.smartestate.model.User;
import com.smartestate.repository.UserRepository;
import com.smartestate.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponseDto signUp(AuthRequestDto authRequestDto) {
        User user = User.builder()
                .email(authRequestDto.email())
                .password(passwordEncoder.encode(authRequestDto.password()))
                .build();

        userRepository.save(user);

        String jwtToken = jwtUtil.generateToken(user);

        return new AuthResponseDto(jwtToken);
    }

    public AuthResponseDto authenticate(AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.email(),
                        authRequestDto.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtil.generateToken(userDetails);

        return new AuthResponseDto(jwtToken);
    }

}
