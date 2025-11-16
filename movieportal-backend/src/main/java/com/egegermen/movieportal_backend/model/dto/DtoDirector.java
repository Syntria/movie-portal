package com.egegermen.movieportal_backend.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoDirector {
    private Long id;
    private String name;
    private String profilePath;
}
