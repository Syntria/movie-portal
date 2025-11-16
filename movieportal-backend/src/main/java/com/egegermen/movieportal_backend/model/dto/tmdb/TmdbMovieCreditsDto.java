package com.egegermen.movieportal_backend.model.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbMovieCreditsDto {
    private List<TmdbCastMemberDto> cast;
    private List<TmdbCrewMemberDto> crew;
}
