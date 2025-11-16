package com.egegermen.movieportal_backend.model.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbCastMemberDto {
    private int id;

    private String name;

    @JsonProperty("profile_path")
    private String profilePath;

    private String character;
}
