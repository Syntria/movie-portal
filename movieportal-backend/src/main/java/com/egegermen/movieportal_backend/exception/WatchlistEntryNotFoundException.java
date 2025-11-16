
package com.egegermen.movieportal_backend.exception;

import com.egegermen.movieportal_backend.model.FavoritesEntry;

public class WatchlistEntryNotFoundException extends EntityNotFoundException {

	public WatchlistEntryNotFoundException(Long id) {
		super(id, FavoritesEntry.class);
	}

	public WatchlistEntryNotFoundException(String message) {
		super(message, FavoritesEntry.class);
	}

	public WatchlistEntryNotFoundException() {
		super("user and movie", FavoritesEntry.class);
	}
}
