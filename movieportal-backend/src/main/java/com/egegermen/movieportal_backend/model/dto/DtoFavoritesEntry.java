package com.egegermen.movieportal_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoFavoritesEntry {
    private Long id;
    private DtoMovie movie;
}
