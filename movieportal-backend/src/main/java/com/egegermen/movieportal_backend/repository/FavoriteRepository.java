package com.egegermen.movieportal_backend.repository;

import com.egegermen.movieportal_backend.model.FavoritesEntry;
import com.egegermen.movieportal_backend.model.Movie;
import com.egegermen.movieportal_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoritesEntry, Long> {
	Optional<FavoritesEntry> findByUserAndMovie(User user, Movie movie);

	List<FavoritesEntry> findByUserOrderByCreatedAtDesc(User user);
}
