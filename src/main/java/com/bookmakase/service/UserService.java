package com.bookmakase.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bookmakase.domain.User;
import com.bookmakase.dto.user.AddressUpdateResponse;
import com.bookmakase.dto.user.InformationUpdateRequest;
import com.bookmakase.dto.user.OneUserResponse;
import com.bookmakase.dto.user.PointUpdateResponse;
import com.bookmakase.dto.user.SignUpRequest;
import com.bookmakase.dto.user.UserResponse;
import com.bookmakase.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	/* ① UserDetailsService 필수 메서드 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() ->
				new UsernameNotFoundException("이메일을 찾을 수 없습니다: " + email));

		return org.springframework.security.core.userdetails.User
			.withUsername(user.getEmail())                    // principal = email
			.password(user.getPassword())
			.authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())))
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

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public Optional<User> findByEmail(String username) {
		return userRepository.findByEmail(username);
	}

	public OneUserResponse getUserById(Long userId) {
		return userRepository.findById(userId)
			.map(user -> OneUserResponse.from(user))
			.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다." + userId));
	}

	@Transactional
	public PointUpdateResponse updatePoint(Long userId, Long point) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다." + userId));

		if (point < 0 && user.getPoint() + point < 0) {
			throw new RuntimeException("현재 포인트가 부족합니다.");
		}

		user.setPoint(user.getPoint() + point);
		userRepository.save(user);

		return PointUpdateResponse.from(user);
	}

	@Transactional
	public AddressUpdateResponse updateAddress(Long userId, String address) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다." + userId));

		user.setAddress(address);
		userRepository.save(user);

		return AddressUpdateResponse.from(user);
	}

	@Transactional
	public UserResponse updateIntro(Long userId, String intro) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다." + userId));

		user.setIntro(intro);
		userRepository.save(user);
		return UserResponse.from(user);
	}

	@Transactional
	public UserResponse updateInformation(Long userId, @Valid InformationUpdateRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다." + userId));

		// 비밀번호 변경
		if (StringUtils.hasText(request.getNewPassword())) {
			if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
				throw new RuntimeException("비밀번호가 서로 다릅니다.");
			}
			if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
				throw new RuntimeException("변경하려는 비밀번호가 서로 다릅니다.");
			}
			user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		}

		// 닉네임 변경
		if (StringUtils.hasText(request.getNewUsername())
			&& !request.getNewUsername().equals(user.getUsername())
		) {
			if (userRepository.existsByUsername(request.getNewUsername())) {
				throw new RuntimeException("변경하려는 닉네임이 중복입니다.");
			}
			user.setUsername(request.getNewUsername());
		}

		userRepository.save(user);
		return UserResponse.from(user);
	}
}
