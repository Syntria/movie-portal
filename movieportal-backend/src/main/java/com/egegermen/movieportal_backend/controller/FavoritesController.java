package com.egegermen.movieportal_backend.controller;

import com.egegermen.movieportal_backend.component.mapper.FavoritesMapper;
import com.egegermen.movieportal_backend.model.dto.DtoFavoritesEntry;
import com.egegermen.movieportal_backend.service.FavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
public class FavoritesController {

	private final FavoriteService favoriteService;

	public FavoritesController(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}

	@PostMapping("/{movieId}")
	public ResponseEntity<Void> addMovieToFavorites(
			@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long movieId) {
		favoriteService.addMovieToFavorites(userDetails.getUsername(), movieId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{movieId}")
	public ResponseEntity<Void> removeMovieFromFavorites(
			@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long movieId) {
		favoriteService.removeMovieFromFavorites(userDetails.getUsername(), movieId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<DtoFavoritesEntry>> getFavorites(
			@AuthenticationPrincipal UserDetails userDetails) {
		List<DtoFavoritesEntry> favorites =
				favoriteService.getFavoriteEntries(userDetails.getUsername())
						.stream()
						.map(FavoritesMapper::toDto)
						.collect(Collectors.toList());
		return ResponseEntity.ok(favorites);
	}
}
