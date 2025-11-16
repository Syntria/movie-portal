package com.egegermen.movieportal_backend.component.mapper;

import com.egegermen.movieportal_backend.model.FavoritesEntry;
import com.egegermen.movieportal_backend.model.dto.DtoFavoritesEntry;

import org.springframework.stereotype.Component;

@Component
public final class FavoritesMapper {

	private FavoritesMapper() {
	}

	public static DtoFavoritesEntry toDto(FavoritesEntry entry) {
		if (entry == null) {
			return null;
		}
		return DtoFavoritesEntry.builder()
				.id(entry.getId())
				.movie(MovieMapper.toDto(entry.getMovie()))
				.build();
	}
}
