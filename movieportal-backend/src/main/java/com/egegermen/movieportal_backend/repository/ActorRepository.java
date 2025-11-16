package com.egegermen.movieportal_backend.repository;

import com.egegermen.movieportal_backend.model.Actor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
	Optional<Actor> findByTmdbId(Integer tmdbId);
}
