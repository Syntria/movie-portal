package com.egegermen.movieportal_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoMovie {

    private Long id;

    private String originalTitle;

    private String overview;

    private String posterPath;

    private String releaseDate;

    private DtoDirector director;

    private List<DtoActor> cast;

}
