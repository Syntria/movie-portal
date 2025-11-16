package com.egegermen.movieportal_backend.service.impl;

import com.egegermen.movieportal_backend.component.mapper.CommentMapper;
import com.egegermen.movieportal_backend.exception.MovieNotFoundException;
import com.egegermen.movieportal_backend.exception.UserNotFoundException;
import com.egegermen.movieportal_backend.model.Comment;
import com.egegermen.movieportal_backend.model.Movie;
import com.egegermen.movieportal_backend.model.User;
import com.egegermen.movieportal_backend.model.dto.DtoComment;
import com.egegermen.movieportal_backend.repository.CommentRepository;
import com.egegermen.movieportal_backend.repository.MovieRepository;
import com.egegermen.movieportal_backend.repository.UserRepository;
import com.egegermen.movieportal_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CommentServiceImpl implements CommentService {

	private final UserRepository userRepository;
	private final MovieRepository movieRepository;
	private final CommentRepository commentRepository;

	@Override
	@Transactional(readOnly = true)
	public List<DtoComment> getCommentsForMovie(Long movieId) {
		return commentRepository.findByMovieIdOrderByCreatedAtDesc(movieId)
				.stream()
				.map(CommentMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public DtoComment addComment(Long movieId, String text, String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username));
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new MovieNotFoundException(movieId));

		Comment comment = Comment.builder()
				.text(text)
				.user(user)
				.movie(movie)
				.build();

		Comment savedComment = commentRepository.save(comment);
		return CommentMapper.toDto(savedComment);
	}

	

	@Override
	public DtoComment updateComment(Long id, String text) {

		Comment comment = commentRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException());

		comment.setText(text);

		Comment savedComment = commentRepository.save(comment);

		return CommentMapper.toDto(savedComment);
	}

}
