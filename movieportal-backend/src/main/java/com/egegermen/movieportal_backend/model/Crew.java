package com.egegermen.movieportal_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@MappedSuperclass
public abstract class Crew extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private Integer tmdbId;

    private String profilePath;
}
