package com.egegermen.movieportal_backend.service.impl;

import com.egegermen.movieportal_backend.exception.EntryAlreadyExistsException;
import com.egegermen.movieportal_backend.exception.FavoritesEntryNotFoundException;
import com.egegermen.movieportal_backend.exception.MovieNotFoundException;
import com.egegermen.movieportal_backend.exception.UserNotFoundException;
import com.egegermen.movieportal_backend.model.Movie;
import com.egegermen.movieportal_backend.model.User;
import com.egegermen.movieportal_backend.model.WatchlistEntry;
import com.egegermen.movieportal_backend.model.enums.WatchStatus;
import com.egegermen.movieportal_backend.repository.MovieRepository;
import com.egegermen.movieportal_backend.repository.UserRepository;
import com.egegermen.movieportal_backend.repository.WatchlistRepository;
import com.egegermen.movieportal_backend.service.WatchlistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class WatchlistServiceImpl implements WatchlistService {

	private final UserRepository userRepository;
	private final MovieRepository movieRepository;
	private final WatchlistRepository watchlistRepository;

	public WatchlistServiceImpl(UserRepository userRepository, MovieRepository movieRepository,
			WatchlistRepository watchlistRepository) {
		this.userRepository = userRepository;
		this.movieRepository = movieRepository;
		this.watchlistRepository = watchlistRepository;
	}

	@Override
	@Transactional
	public void addMovieToWatchlist(String username, Long movieId) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new MovieNotFoundException(movieId));

		if (watchlistRepository.findByUserAndMovie(user, movie).isPresent()) {
			throw new EntryAlreadyExistsException("Movie", "watchlist");
		}

		WatchlistEntry entry = new WatchlistEntry(user, movie, WatchStatus.WILL_WATCH);
		watchlistRepository.save(entry);
	}

	@Override
	@Transactional
	public void updateWatchStatus(String username, Long movieId, WatchStatus status) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new MovieNotFoundException(movieId));

		WatchlistEntry entry = watchlistRepository.findByUserAndMovie(user, movie)
				.orElseThrow(() -> new FavoritesEntryNotFoundException("watchlist"));

		entry.setStatus(status);
		watchlistRepository.save(entry);
	}

	@Override
	@Transactional
	public void removeMovieFromWatchlist(String username, Long movieId) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new MovieNotFoundException(movieId));

		WatchlistEntry entry = watchlistRepository.findByUserAndMovie(user, movie)
				.orElseThrow(() -> new FavoritesEntryNotFoundException("watchlist"));

		watchlistRepository.delete(entry);
	}

	@Override
	@Transactional(readOnly = true)
	public List<WatchlistEntry> getWatchlist(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		return watchlistRepository.findByUserOrderByCreatedAtDesc(user);
	}
}
