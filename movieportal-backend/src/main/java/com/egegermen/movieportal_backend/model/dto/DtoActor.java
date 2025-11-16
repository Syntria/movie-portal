package com.egegermen.movieportal_backend.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoActor {
    private Long id;

    private String name;

    private String profilePath;

    private String characterName;
}
