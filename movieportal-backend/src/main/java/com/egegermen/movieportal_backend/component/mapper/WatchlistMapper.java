package com.egegermen.movieportal_backend.component.mapper;

import com.egegermen.movieportal_backend.model.WatchlistEntry;
import com.egegermen.movieportal_backend.model.dto.DtoWatchlistEntry;

import org.springframework.stereotype.Component;

@Component
public final class WatchlistMapper {

	private WatchlistMapper() {
	}

	public static DtoWatchlistEntry toDto(WatchlistEntry entry) {
		if (entry == null) {
			return null;
		}
		return DtoWatchlistEntry.builder()
				.id(entry.getId())
				.movie(MovieMapper.toDto(entry.getMovie()))
				.status(entry.getStatus())
				.build();
	}
}
