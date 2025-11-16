package com.egegermen.movieportal_backend.component.mapper;

import com.egegermen.movieportal_backend.model.Comment;
import com.egegermen.movieportal_backend.model.dto.DtoComment;

import org.springframework.stereotype.Component;

@Component
public final class CommentMapper {
	private CommentMapper() {
	}

	public static DtoComment toDto(Comment comment) {
		return DtoComment.builder()
				.id(comment.getId())
				.text(comment.getText())
				.username(comment.getUser().getUsername())
				.createdAt(comment.getCreatedAt())
				.build();
	}
}
