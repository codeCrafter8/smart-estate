package com.smartestate.service;

import com.smartestate.exception.ResourceNotFoundException;
import com.smartestate.model.User;
import com.smartestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private static final String USER_NOT_FOUND_MSG = "User with email %s not found";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Attempting to load user with email: {}", username);

        UserDetails user = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    log.warn("Authentication failed. No user found with email: {}", username);
                    return new UsernameNotFoundException(
                            String.format(USER_NOT_FOUND_MSG, username));
                });

        log.info("User with email {} successfully loaded", username);
        return user;
    }

    public User findByEmail(String email) {
        log.info("Fetching user with email: {}", email);

        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User with email {} not found", email);
                    return new ResourceNotFoundException(
                            String.format(USER_NOT_FOUND_MSG, email));
                });
    }
}
