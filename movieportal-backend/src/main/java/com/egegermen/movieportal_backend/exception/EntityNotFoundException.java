package com.egegermen.movieportal_backend.exception;

public class EntityNotFoundException extends RuntimeException {
	public EntityNotFoundException(Long id, Class<?> clazz) {
		super(clazz.getSimpleName() + " with ID " + id + " not found.");
	}

	public EntityNotFoundException(String name, Class<?> clazz) {
		super(clazz.getSimpleName() + " with name " + name + " not found.");
	}
}


