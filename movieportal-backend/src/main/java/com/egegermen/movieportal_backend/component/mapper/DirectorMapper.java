package com.egegermen.movieportal_backend.component.mapper;

import com.egegermen.movieportal_backend.model.Director;
import com.egegermen.movieportal_backend.model.dto.DtoDirector;
import com.egegermen.movieportal_backend.model.dto.DtoDirectorDetails;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class DirectorMapper {

	public static DtoDirector toDto(Director director) {
		if (director == null) {
			return null;
		}
		return DtoDirector.builder()
				.id(director.getId())
				.name(director.getName())
				.profilePath(director.getProfilePath())
				.build();
	}

	public static DtoDirectorDetails toDetailsDto(Director director) {
		if (director == null)
			return null;
		return DtoDirectorDetails.builder()
				.id(director.getId())
				.name(director.getName())
				.profilePath(director.getProfilePath())
				// We must map the movies here, but avoid the infinite loop
				.movies(new ArrayList<>(director.getMovies()).stream()
						.map(MovieMapper::toSimpleDto) // Use a new simple DTO to prevent loops
						.collect(Collectors.toList()))
				.build();
	}
}
