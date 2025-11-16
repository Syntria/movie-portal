package com.egegermen.movieportal_backend.model.dto;

import com.egegermen.movieportal_backend.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser {

    private Long id;
    private String username;

    private List<Movie> watchlist;
    private List<Movie> favoriteList;

}
