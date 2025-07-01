package com.bookmakase.dto.user;


import com.bookmakase.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long userId;
    private String username;
    private String email;

    private LocalDateTime createdAt;
    private String imageUrl;
    private String intro;
    private String phone;
    private String address;
    private Long point;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .imageUrl(user.getImageUrl())
                .intro(user.getIntro())
                .phone(user.getPhone())
                .address(user.getAddress())
                .point(user.getPoint())
                .build();
    }

}
