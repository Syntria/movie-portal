package com.egegermen.movieportal_backend.repository;

import com.egegermen.movieportal_backend.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByMovieIdOrderByCreatedAtDesc(Long movieId);
}
