package com.egegermen.movieportal_backend.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoMovieIU {

    private String originalTitle;

    private String overview;

    private String posterPath;

    private String releaseDate;
}


