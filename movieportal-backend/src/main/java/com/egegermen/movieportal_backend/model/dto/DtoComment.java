package com.egegermen.movieportal_backend.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DtoComment {
    private Long id;
    private String text;
    private String username;
    private LocalDateTime createdAt;
}
