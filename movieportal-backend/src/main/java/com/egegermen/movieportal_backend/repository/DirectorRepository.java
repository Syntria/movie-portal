package com.egegermen.movieportal_backend.repository;

import com.egegermen.movieportal_backend.model.Director;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
	Optional<Director> findByTmdbId(Integer tmdbId);
}
