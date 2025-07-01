package com.bookmakase.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			// CSRF : 사용 안 함 (REST용)

			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			// CORS : 모든 origin/method 허용

			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			// 세션 : 세션을 생성하거나 사용하지 않음 (stateless), JWT 기반 인증 구조

			.authorizeHttpRequests(authz -> authz
					.requestMatchers("/api/v1/admin/**").permitAll()
					.anyRequest().permitAll()
				// 인증 : 전체 공개 (특정 경로만 보호하려면 수정 필요)
				// /api/v1/admin/** → 인증 없이도 누구나 접근 허용
				// 나머지 요청도 모두 permitAll() → 전체 공개
				// 현재는 인증 로직이 없는 상태이므로 테스트/개발 환경용 설정입니다.

				// 실제 운영시 권장 설정 예
				// .authorizeHttpRequests(authz -> authz
				//     .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
				//     .requestMatchers("/api/v1/user/**").hasRole("USER")
				//     .anyRequest().authenticated()
				// )
			);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost:3001")); // ✅ 프론트 도메인 허용
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true); // ✅ Authorization 헤더 포함 허용

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
