package com.egegermen.movieportal_backend.component.mapper;

import com.egegermen.movieportal_backend.model.Movie;
import com.egegermen.movieportal_backend.model.dto.DtoActor;
import com.egegermen.movieportal_backend.model.dto.DtoMovie;
import com.egegermen.movieportal_backend.model.dto.DtoMovieIU;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public final class MovieMapper {

	private MovieMapper() {}

	public static DtoMovie toDto(Movie movie) {
		if (movie == null) {
			return null;
		}

		return DtoMovie.builder()
				.id(movie.getId())
				.originalTitle(movie.getOriginalTitle())
				.overview(movie.getOverview())
				.posterPath(movie.getPosterPath())
				.releaseDate(movie.getReleaseDate())
				.director(DirectorMapper.toDto(movie.getDirector()))
				.cast(
						movie.getMovieCharacters().stream()
								.map(mc -> DtoActor.builder()
										.id(mc.getActor().getId())
										.name(mc.getActor().getName())
										.profilePath(mc.getActor().getProfilePath())
										.characterName(mc.getCharacterName())
										.build())
								.collect(Collectors.toList()))
				.build();
	}

	public static Movie toEntity(DtoMovieIU dto) {
		if (dto == null) {
			return null;
		}
		return Movie.builder()
				.originalTitle(dto.getOriginalTitle())
				.overview(dto.getOverview())
				.posterPath(dto.getPosterPath())
				.releaseDate(dto.getReleaseDate())
				.build();
	}

	public static DtoMovie toSimpleDto(Movie movie) {
		if (movie == null) {
			return null;
		}
		return DtoMovie.builder()
				.id(movie.getId())
				.originalTitle(movie.getOriginalTitle())
				.posterPath(movie.getPosterPath())
				.releaseDate(movie.getReleaseDate()) // Not mapping director or cast since we dont
														// need for directory page
				.build();
	}
}
