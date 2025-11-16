package com.egegermen.movieportal_backend.service;

import com.egegermen.movieportal_backend.model.dto.DtoComment;
import java.util.List;

public interface CommentService {
	List<DtoComment> getCommentsForMovie(Long movieId);

	DtoComment addComment(Long movieId, String text, String username);

	DtoComment updateComment(Long Id, String text);
}
