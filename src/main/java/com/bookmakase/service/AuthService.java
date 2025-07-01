package com.bookmakase.service;


import com.bookmakase.domain.User;
import com.bookmakase.dto.user.SignUpRequest;
import com.bookmakase.dto.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public UserResponse register(@Valid SignUpRequest signUpRequest) {
       if(userService.existsByEmail(signUpRequest.getEmail())) {
           throw new RuntimeException("Email already exists");
       }

       // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());


        User.Role role = User.Role.USER;

       // User 엔티티로 저장
        UserResponse savedUser = userService.createUser(signUpRequest, encodedPassword, role);


       return savedUser;
    }


}
