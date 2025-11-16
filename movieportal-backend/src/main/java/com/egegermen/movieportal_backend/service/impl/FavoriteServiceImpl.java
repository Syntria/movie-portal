package com.egegermen.movieportal_backend.service.impl;

import com.egegermen.movieportal_backend.exception.EntryAlreadyExistsException;
import com.egegermen.movieportal_backend.exception.FavoritesEntryNotFoundException;
import com.egegermen.movieportal_backend.exception.MovieNotFoundException;
import com.egegermen.movieportal_backend.exception.UserNotFoundException;
import com.egegermen.movieportal_backend.model.FavoritesEntry;
import com.egegermen.movieportal_backend.model.Movie;
import com.egegermen.movieportal_backend.model.User;
import com.egegermen.movieportal_backend.repository.FavoriteRepository;
import com.egegermen.movieportal_backend.repository.MovieRepository;
import com.egegermen.movieportal_backend.repository.UserRepository;
import com.egegermen.movieportal_backend.service.FavoriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	private final UserRepository userRepository;
	private final MovieRepository movieRepository;
	private final FavoriteRepository favoriteRepository;

	public FavoriteServiceImpl(UserRepository userRepository, MovieRepository movieRepository,
			FavoriteRepository favoriteRepository) {
		this.userRepository = userRepository;
		this.movieRepository = movieRepository;
		this.favoriteRepository = favoriteRepository;
	}

	@Override
	@Transactional
	public void addMovieToFavorites(String username, Long movieId) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new MovieNotFoundException(movieId));

		if (favoriteRepository.findByUserAndMovie(user, movie).isPresent()) {
			throw new EntryAlreadyExistsException("Movie", "favorites");
		}

		FavoritesEntry entry = new FavoritesEntry(user, movie);
		favoriteRepository.save(entry);
	}

	@Override
	@Transactional
	public void removeMovieFromFavorites(String username, Long movieId) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new MovieNotFoundException(movieId));

		FavoritesEntry entry = favoriteRepository.findByUserAndMovie(user, movie)
				.orElseThrow(FavoritesEntryNotFoundException::new);

		favoriteRepository.delete(entry);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FavoritesEntry> getFavoriteEntries(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		return favoriteRepository.findByUserOrderByCreatedAtDesc(user);
	}
}
