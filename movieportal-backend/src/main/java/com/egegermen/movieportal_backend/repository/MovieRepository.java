package com.egegermen.movieportal_backend.repository;

import com.egegermen.movieportal_backend.model.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

	void deleteByTmdbId(int tmdbId);

	@Query("SELECT m.tmdbId FROM Movie m WHERE m.tmdbId IS NOT NULL")
	List<Integer> findAllTmdbIds();

	Optional<Movie> findByTmdbId(Integer tmdbId);
}
