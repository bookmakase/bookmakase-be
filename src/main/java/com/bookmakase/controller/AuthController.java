package com.bookmakase.controller;


import com.bookmakase.dto.user.JwtResponse;
import com.bookmakase.dto.user.LoginRequest;
import com.bookmakase.dto.user.SignUpRequest;
import com.bookmakase.dto.user.TokenRefreshRequest;
import com.bookmakase.dto.user.TokenRefreshResponse;
import com.bookmakase.dto.user.UserResponse;
import com.bookmakase.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        UserResponse userResponse = authService.register(signUpRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody TokenRefreshRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        String newAccessToken = authService.refreshAccessToken(tokenRefreshRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body(new TokenRefreshResponse(newAccessToken, tokenRefreshRequest.getRefreshToken()));
    }


}
