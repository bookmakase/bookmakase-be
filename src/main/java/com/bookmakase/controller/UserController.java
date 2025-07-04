package com.bookmakase.controller;


import com.bookmakase.domain.User;
import com.bookmakase.dto.user.AddressUpdateRequest;
import com.bookmakase.dto.user.AddressUpdateResponse;
import com.bookmakase.dto.user.IntroUpdateRequest;
import com.bookmakase.dto.user.OneUserResponse;
import com.bookmakase.dto.user.PointUpdateRequest;
import com.bookmakase.dto.user.PointUpdateResponse;
import com.bookmakase.dto.user.UserResponse;
import com.bookmakase.service.AuthService;
import com.bookmakase.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(UserResponse.from(authService.getCurrentUser()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<OneUserResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
    }

    @PatchMapping("/point")
    public ResponseEntity<PointUpdateResponse> patchUserPoint(@RequestBody @Valid PointUpdateRequest request) {
        User current = authService.getCurrentUser();
        if(current == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        PointUpdateResponse updated = userService.updatePoint(current.getUserId(), request.getPoint());
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @PatchMapping("/address")
    public ResponseEntity<AddressUpdateResponse> patchUserAddress(@RequestBody @Valid AddressUpdateRequest request) {
        User current = authService.getCurrentUser();

        if(current == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        AddressUpdateResponse updated = userService.updateAddress(current.getUserId(), request.getAddress());
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @PatchMapping("/intro")
    public ResponseEntity<UserResponse> patchUserIntro(@RequestBody @Valid IntroUpdateRequest request) {
        User current = authService.getCurrentUser();

        if(current == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserResponse updated = userService.updateIntro(current.getUserId(), request.getIntro());
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
}

