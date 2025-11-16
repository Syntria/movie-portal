package com.egegermen.movieportal_backend.service;

import com.egegermen.movieportal_backend.model.dto.DtoMovie;
import com.egegermen.movieportal_backend.model.dto.DtoMovieIU;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MovieService {

	DtoMovie saveMovie(DtoMovieIU movieDto);

	Page<DtoMovie> getAllMovies(Pageable pageable);

	public Page<DtoMovie> searchMovies(String title, Integer year, String yearFilterType, Pageable pageable);

	Optional<DtoMovie> getMovieById(Long id);

	Optional<DtoMovie> updateMovie(Long id, DtoMovieIU movieDto);

	void deleteMovie(Long id);

	DtoMovie addMovieFromTmdb(int tmdbId);

	List<Integer> getAllTmdbIds();

	void deleteMovieByTmdbId(int tmdbId);
}
