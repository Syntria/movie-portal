package com.egegermen.movieportal_backend.service;

import com.egegermen.movieportal_backend.model.WatchlistEntry;
import com.egegermen.movieportal_backend.model.enums.WatchStatus;
import java.util.List;

public interface WatchlistService {

	void addMovieToWatchlist(String username, Long movieId);

	void updateWatchStatus(String username, Long movieId, WatchStatus status);

	void removeMovieFromWatchlist(String username, Long movieId);

	List<WatchlistEntry> getWatchlist(String username);
}
