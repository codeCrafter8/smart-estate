package com.smartestate.service;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Attempting to load user with email: {}", username);

        UserDetails user = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    log.warn("User with email {} not found", username);
                    return new UsernameNotFoundException(
                            String.format("User with email %s not found", username));
                });

        log.info("User with email {} successfully loaded", username);
        return user;
    }
}
