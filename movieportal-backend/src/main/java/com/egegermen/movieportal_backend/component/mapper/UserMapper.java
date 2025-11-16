package com.egegermen.movieportal_backend.component.mapper;

import com.egegermen.movieportal_backend.model.User;
import com.egegermen.movieportal_backend.model.dto.DtoUser;
import com.egegermen.movieportal_backend.model.dto.DtoUserIU;

import org.springframework.stereotype.Component;

@Component
public final class UserMapper {

	private UserMapper() {
	}

	public static DtoUser toDto(User user) {
		if (user == null) {
			return null;
		}
		return DtoUser.builder()
				.id(user.getId())
				.username(user.getUsername())
				.build();
	}

	public static User toEntity(DtoUserIU dtoUserIU) {
		if (dtoUserIU == null) {
			return null;
		}
		return User.builder()
				.id(dtoUserIU.getId())
				.username(dtoUserIU.getUsername())
				.password(dtoUserIU.getPassword())
				.build();
	}
}
