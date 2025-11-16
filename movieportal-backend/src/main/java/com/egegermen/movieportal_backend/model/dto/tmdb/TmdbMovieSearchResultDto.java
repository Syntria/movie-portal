package com.egegermen.movieportal_backend.model.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Prevents errors if the API response contains unmapped fields
public class TmdbMovieSearchResultDto {

    private int page;

    private List<TmdbMovieResultDto> results;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_results")
    private int totalResults;
}
