package com.bookmakase.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Email
    @Column(name = "email", nullable = false, unique = true, length = 254)
    private String email;


    @Column(name = "password", nullable = false, length = 100)
    private String password;


    @Column(nullable = false, length = 10)
    private String username;

    @CreatedDate
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long point = 0L;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.USER;


    private String address;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String intro;


    public enum Role { USER, ADMIN }

    @OneToMany(mappedBy = "review")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "comment")
    private List<Comment> comments = new ArrayList<>();
}
