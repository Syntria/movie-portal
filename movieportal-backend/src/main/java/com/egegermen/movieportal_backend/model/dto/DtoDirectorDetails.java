package com.egegermen.movieportal_backend.model.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class DtoDirectorDetails { //This class is created becuse retrieving movies on DTODirector is uncessary load
    private Long id;

    private String name;

    private String profilePath;

    private List<DtoMovie> movies;
}
