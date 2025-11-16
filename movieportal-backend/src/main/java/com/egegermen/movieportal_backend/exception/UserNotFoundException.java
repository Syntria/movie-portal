package com.egegermen.movieportal_backend.exception;

import com.egegermen.movieportal_backend.model.User;

public class UserNotFoundException extends EntityNotFoundException {
	public UserNotFoundException(Long id) {
		super(id, User.class);
	}

	public UserNotFoundException(String username) {
		super(username, User.class);
	}
}
