package com.egegermen.movieportal_backend.component;


import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PasswordHasher {
	private final PasswordEncoder passwordEncoder;

	public PasswordHasher() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	public String hashPassword(String password) {
		return passwordEncoder.encode(password);
	}

	public boolean matches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

}
