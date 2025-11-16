package com.egegermen.movieportal_backend.controller;

import com.egegermen.movieportal_backend.component.mapper.WatchlistMapper;
import com.egegermen.movieportal_backend.model.dto.DtoWatchlistEntry;
import com.egegermen.movieportal_backend.model.dto.DtoWatchlistEntryIU;
import com.egegermen.movieportal_backend.service.WatchlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

	private final WatchlistService watchlistService;

	public WatchlistController(WatchlistService watchlistService) {
		this.watchlistService = watchlistService;
	}

	@PostMapping("/{movieId}")
	public ResponseEntity<Void> addMovieToWatchlist(
			@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long movieId) {
		watchlistService.addMovieToWatchlist(userDetails.getUsername(), movieId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping("/{movieId}")
	public ResponseEntity<Void> updateWatchStatus(
			@AuthenticationPrincipal UserDetails userDetails,
			@PathVariable Long movieId,
			@RequestBody DtoWatchlistEntryIU dto) {
		watchlistService.updateWatchStatus(userDetails.getUsername(), movieId, dto.getStatus());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{movieId}")
	public ResponseEntity<Void> removeMovieFromWatchlist(
			@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long movieId) {
		watchlistService.removeMovieFromWatchlist(userDetails.getUsername(), movieId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<DtoWatchlistEntry>> getWatchlist(
			@AuthenticationPrincipal UserDetails userDetails) {
		List<DtoWatchlistEntry> watchlist = watchlistService.getWatchlist(userDetails.getUsername())
				.stream()
				.map(WatchlistMapper::toDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(watchlist);
	}
}
