package com.egegermen.movieportal_backend.component.mapper;

import com.egegermen.movieportal_backend.model.Actor;
import com.egegermen.movieportal_backend.model.dto.DtoActor;

import org.springframework.stereotype.Component;

@Component
public class ActorMapper {

	public static DtoActor toDto(Actor actor) {
		if (actor == null) {
			return null;
		}
		return DtoActor.builder()
				.id(actor.getId())
				.name(actor.getName())
				.profilePath(actor.getProfilePath())
				.build();
	}
}
