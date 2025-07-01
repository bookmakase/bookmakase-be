package com.bookmakase.service;



import com.bookmakase.domain.User;
import com.bookmakase.dto.user.SignUpRequest;
import com.bookmakase.dto.user.UserResponse;
import com.bookmakase.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserResponse createUser(@Valid SignUpRequest signUpRequest, String encodedPassword, User.Role role) {
    User user = User.builder()
            .email(signUpRequest.getEmail())
            .password(encodedPassword)
            .username(signUpRequest.getUsername())
            .point(0L)
            .phone(signUpRequest.getPhone())
            .role(role)
            .build();

        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);

    }

}
