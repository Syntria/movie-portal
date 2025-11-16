package com.egegermen.movieportal_backend.controller;

import com.egegermen.movieportal_backend.model.dto.DtoMovie;
import com.egegermen.movieportal_backend.model.dto.DtoMovieIU;
import com.egegermen.movieportal_backend.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

	private final MovieService movieService;


	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<DtoMovie> createMovie(@RequestBody DtoMovieIU movieDto) {
		DtoMovie createdMovie = movieService.saveMovie(movieDto);
		return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
	}

	@PostMapping("/tmdb/{tmdbId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<DtoMovie> addMovieFromTmdb(@PathVariable int tmdbId) {
		DtoMovie createdMovie = movieService.addMovieFromTmdb(tmdbId);
		return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<DtoMovie> updateMovie(@PathVariable Long id,
			@RequestBody DtoMovieIU movieDto) {
		return movieService.updateMovie(id, movieDto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
		movieService.deleteMovie(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<Page<DtoMovie>> searchMovies(
			@RequestParam(required = false) String title,
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) String yearFilterType,
			Pageable pageable) {

		return ResponseEntity.ok(movieService.searchMovies(title, year, yearFilterType, pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DtoMovie> getMovieById(@PathVariable Long id) {
		return movieService.getMovieById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

    @GetMapping("/tmdb-ids")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Integer>> getAllTmdbIdsInPortal() {
        return ResponseEntity.ok(movieService.getAllTmdbIds());
    }

    @DeleteMapping("/tmdb/{tmdbId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMovieByTmdbId(@PathVariable int tmdbId) {
        movieService.deleteMovieByTmdbId(tmdbId);
        return ResponseEntity.noContent().build();
    }
}
