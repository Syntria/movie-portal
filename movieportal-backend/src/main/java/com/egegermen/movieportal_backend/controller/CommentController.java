package com.egegermen.movieportal_backend.controller;

import com.egegermen.movieportal_backend.model.dto.DtoComment;
import com.egegermen.movieportal_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies/{movieId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<DtoComment>> getComments(@PathVariable Long movieId) {
        return ResponseEntity.ok(commentService.getCommentsForMovie(movieId));
    }

    @PostMapping
    public ResponseEntity<DtoComment> addComment(@PathVariable Long movieId,
                                                 @RequestBody Map<String, String> payload,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        String text = payload.get("text");
        DtoComment newComment = commentService.addComment(movieId, text, userDetails.getUsername());
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }


}
