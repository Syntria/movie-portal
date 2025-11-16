package com.egegermen.movieportal_backend.repository;

import com.egegermen.movieportal_backend.model.Movie;
import com.egegermen.movieportal_backend.model.User;
import com.egegermen.movieportal_backend.model.WatchlistEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<WatchlistEntry, Long> {
	Optional<WatchlistEntry> findByUserAndMovie(User user, Movie movie);

	List<WatchlistEntry> findByUserOrderByCreatedAtDesc(User user);
}
