package com.egegermen.movieportal_backend.security;

import com.egegermen.movieportal_backend.component.PasswordHasher;
import com.egegermen.movieportal_backend.model.dto.DtoUser;
import com.egegermen.movieportal_backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain springFilterChain(HttpSecurity http, UserService userService,
			ObjectMapper objectMapper)
			throws Exception {
		return http
				.cors(cors -> cors.configurationSource(request -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(List.of("http://localhost:3000"));
					config.setAllowedMethods(
							List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
					config.setAllowedHeaders(List.of("*"));
					config.setAllowCredentials(true);
					return config;
				}))
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

						.requestMatchers("/api/users/login").permitAll()
						.requestMatchers("/api/users").permitAll()
						.requestMatchers("/api/tmdb/**").hasRole("ADMIN")
						.requestMatchers("/api/movies/{movieId}/comments")
						.hasAnyRole("USER", "ADMIN")
						.requestMatchers("/api/movies/tmdb-ids").hasRole("ADMIN")
						.requestMatchers("/api/movies/tmdb/{tmdbId}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/api/directors/**")
						.hasAnyRole("USER", "ADMIN")
						.anyRequest().authenticated())
				.formLogin(form -> {
					form.loginProcessingUrl("/api/users/login");
					form.successHandler((req, res, auth) -> {
						DtoUser userDto = userService.findUserByUsername(auth.getName())
								.orElseThrow(() -> new RuntimeException(
										"Authenticated user not found in database"));
						res.setStatus(HttpStatus.OK.value());
						res.setContentType("application/json");
						res.getWriter().write(objectMapper.writeValueAsString(userDto));
					});
					form.failureHandler(
							(req, res, ex) -> res.setStatus(HttpStatus.UNAUTHORIZED.value()));
				})
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder(PasswordHasher passwordHasher) {
		return passwordHasher.getPasswordEncoder();
	}
}
