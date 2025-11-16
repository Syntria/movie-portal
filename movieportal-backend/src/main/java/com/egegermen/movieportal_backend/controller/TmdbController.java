package com.egegermen.movieportal_backend.controller;

import com.egegermen.movieportal_backend.model.dto.tmdb.TmdbMovieSearchResultDto;
import com.egegermen.movieportal_backend.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tmdb")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class TmdbController {

    private final TmdbService tmdbService;

    @GetMapping("/popular")
    public ResponseEntity<TmdbMovieSearchResultDto> getPopularMovies(
            @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(tmdbService.getPopularMovies(page));
    }

    @GetMapping("/search")
    public ResponseEntity<TmdbMovieSearchResultDto> searchMovies(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(tmdbService.searchMovies(query, page));
    }
}
