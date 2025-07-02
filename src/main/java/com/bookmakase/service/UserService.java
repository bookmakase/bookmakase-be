package com.bookmakase.service;



import com.bookmakase.domain.User;
import com.bookmakase.dto.user.AddressUpdateResponse;
import com.bookmakase.dto.user.OneUserResponse;
import com.bookmakase.dto.user.PointUpdateResponse;
import com.bookmakase.dto.user.SignUpRequest;
import com.bookmakase.dto.user.UserResponse;
import com.bookmakase.repository.UserRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;


    /* ① UserDetailsService 필수 메서드 */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("이메일을 찾을 수 없습니다: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())                    // principal = email
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .build();
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



    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername( String username) {
        return userRepository.existsByUsername(username);
    }

    public Optional<User> findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

	public OneUserResponse getUserById(Long userId) {
        return userRepository.findById(userId)
            .map(user-> OneUserResponse.from(user))
            .orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다." + userId));
	}

    @Transactional
    public PointUpdateResponse updatePoint(Long userId, Long point) {
        User user = userRepository.findById(userId)
            .orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다." + userId));

       if (point < 0 && user.getPoint() + point < 0) {
           throw new RuntimeException("현재 포인트가 부족합니다.");
       }

       user.setPoint(user.getPoint() + point);
       userRepository.save(user);

       return PointUpdateResponse.from(user);
    }

    public AddressUpdateResponse updateAddress(Long userId, String address) {
        User user = userRepository.findById(userId)
            .orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다." + userId));

        user.setAddress(address);
        userRepository.save(user);

        return AddressUpdateResponse.from(user);
    }
}
