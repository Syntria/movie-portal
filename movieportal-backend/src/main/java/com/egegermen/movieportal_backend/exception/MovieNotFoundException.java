package com.egegermen.movieportal_backend.exception;

import com.egegermen.movieportal_backend.model.Movie;

public class MovieNotFoundException extends EntityNotFoundException {

	public MovieNotFoundException(Long id) {
		super(id, Movie.class);
	}

	public MovieNotFoundException(String title) {
		super(title, Movie.class);
	}
}
