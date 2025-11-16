package com.egegermen.movieportal_backend.model.dto;

import com.egegermen.movieportal_backend.model.enums.WatchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoWatchlistEntry {
    private Long id;
    private DtoMovie movie;
    private WatchStatus status;
}
