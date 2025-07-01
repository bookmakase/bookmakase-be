package com.bookmakase.service;



import com.bookmakase.domain.RefreshToken;
import com.bookmakase.domain.User;
import com.bookmakase.dto.user.JwtResponse;
import com.bookmakase.dto.user.LoginRequest;
import com.bookmakase.dto.user.SignUpRequest;
import com.bookmakase.dto.user.UserResponse;
import com.bookmakase.exception.DuplicateEmailException;
import com.bookmakase.exception.DuplicateUsernameException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final com.bookmakase.utils.JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;


    @Transactional
    public UserResponse register(@Valid SignUpRequest signUpRequest) {

        if (userService.existsByUsername(signUpRequest.getUsername())) {
            throw new DuplicateUsernameException();
        }


       if(userService.existsByEmail(signUpRequest.getEmail())) {
           throw new DuplicateEmailException();
       }

       // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());


        User.Role role = User.Role.USER;

       // User 엔티티로 저장
        UserResponse savedUser = userService.createUser(signUpRequest, encodedPassword, role);


       return savedUser;
    }


    @Transactional
    public JwtResponse login(@Valid LoginRequest loginRequest) {

        // (1) AuthenticationManager를 사용하여 사용자 인증을 수행합니다.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // (2) 인증에 성공하면, SecurityContextHolder에 인증 정보를 저장합니다.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // (3) 인증된 사용자 정보를 기반으로 Access Token(JwtUtil.generateToken)과 Refresh Token(RefreshTokenService.createRefreshToken)을 생성합니다.
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtUtil.generateToken(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        // (4) 생성된 토큰들을 JwtResponse DTO에 담아 반환합니다.
        return new JwtResponse(accessToken, refreshToken.getToken());
    }

    // 내 정보 조회하기
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userService.getUserByUsername(username).orElse(null);
        }

        return null;
    }

    public String refreshAccessToken(String refreshToken) {
        String newAccessToken = refreshTokenService.findByToken(refreshToken)
                .map(token-> refreshTokenService.verifyExpiration(token))
                .map(token->token.getUser())
                .map(user-> jwtUtil.generateToken(
                        org.springframework.security.core.userdetails.User
                                .withUsername(user.getUsername())
                                .password(user.getPassword())
                                .authorities("ROLE_" + user.getRole().name())
                                .build()))
                .orElseThrow(()-> new RuntimeException("리프레쉬 토큰이 없습니다."));

                return newAccessToken;
    }

}
