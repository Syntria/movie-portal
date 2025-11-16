package com.egegermen.movieportal_backend.model.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbMovieDetailsDto {      //This class is used to retrieve details about 1 movie. You can add more field If you want

    private int id;

    @JsonProperty("title")
    private String title;

    private String overview;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("release_date")
    private String releaseDate;
}
