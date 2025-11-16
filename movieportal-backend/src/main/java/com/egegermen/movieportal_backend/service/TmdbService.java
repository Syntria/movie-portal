package com.egegermen.movieportal_backend.service;

import com.egegermen.movieportal_backend.model.dto.tmdb.TmdbMovieCreditsDto;
import com.egegermen.movieportal_backend.model.dto.tmdb.TmdbMovieDetailsDto;
import com.egegermen.movieportal_backend.model.dto.tmdb.TmdbMovieSearchResultDto;

public interface TmdbService {
	TmdbMovieDetailsDto getMovieDetails(int tmdbId);

	TmdbMovieCreditsDto getMovieCredits(int tmdbId);

	TmdbMovieSearchResultDto getPopularMovies(int page);

	TmdbMovieSearchResultDto searchMovies(String query, int page);
}
