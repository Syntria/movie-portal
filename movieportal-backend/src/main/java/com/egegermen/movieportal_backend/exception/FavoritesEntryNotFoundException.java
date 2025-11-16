package com.egegermen.movieportal_backend.exception;

import com.egegermen.movieportal_backend.model.FavoritesEntry;

public class FavoritesEntryNotFoundException extends EntityNotFoundException {

	public FavoritesEntryNotFoundException(Long id) {
		super(id, FavoritesEntry.class);
	}

	public FavoritesEntryNotFoundException(String message) {
		super(message, FavoritesEntry.class);
	}

	public FavoritesEntryNotFoundException() {
		super("user and movie", FavoritesEntry.class);
	}
}
