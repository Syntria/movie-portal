package com.egegermen.movieportal_backend.service;

import com.egegermen.movieportal_backend.model.FavoritesEntry;
import java.util.List;

public interface FavoriteService {

	void addMovieToFavorites(String username, Long movieId);

	void removeMovieFromFavorites(String username, Long movieId);

	List<FavoritesEntry> getFavoriteEntries(String username);
}
