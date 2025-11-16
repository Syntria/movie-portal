package com.egegermen.movieportal_backend.service.impl;

import com.egegermen.movieportal_backend.model.dto.tmdb.TmdbMovieCreditsDto;
import com.egegermen.movieportal_backend.model.dto.tmdb.TmdbMovieDetailsDto;
import com.egegermen.movieportal_backend.model.dto.tmdb.TmdbMovieSearchResultDto;
import com.egegermen.movieportal_backend.service.TmdbService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TmdbServiceImpl implements TmdbService {

	private final RestTemplate restTemplate;

	@Value("${tmdb.api.key}")
	private String apiKey;

	private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";

	public TmdbServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public TmdbMovieDetailsDto getMovieDetails(int tmdbId) {
		String url = UriComponentsBuilder.fromHttpUrl(TMDB_API_BASE_URL)
				.pathSegment("movie", String.valueOf(tmdbId))
				.queryParam("api_key", apiKey)
				.toUriString();
		return restTemplate.getForObject(url, TmdbMovieDetailsDto.class);
	}

	@Override
	public TmdbMovieCreditsDto getMovieCredits(int tmdbId) {
		String url = UriComponentsBuilder.fromHttpUrl(TMDB_API_BASE_URL)
				.pathSegment("movie", String.valueOf(tmdbId), "credits")
				.queryParam("api_key", apiKey)
				.toUriString();
		return restTemplate.getForObject(url, TmdbMovieCreditsDto.class);
	}

	@Override
	public TmdbMovieSearchResultDto getPopularMovies(int page) {
		String url = UriComponentsBuilder.fromHttpUrl(TMDB_API_BASE_URL)
				.path("/movie/popular")
				.queryParam("api_key", apiKey)
				.queryParam("page", page)
				.build()
				.toUriString();

		return restTemplate.getForObject(url, TmdbMovieSearchResultDto.class);
	}

	@Override
	public TmdbMovieSearchResultDto searchMovies(String query, int page) {
		String url = UriComponentsBuilder.fromHttpUrl(TMDB_API_BASE_URL)
				.path("/search/movie")
				.queryParam("api_key", apiKey)
				.queryParam("query", query)
				.queryParam("page", page)
				.build()
				.toUriString();

		return restTemplate.getForObject(url, TmdbMovieSearchResultDto.class);
	}
}
