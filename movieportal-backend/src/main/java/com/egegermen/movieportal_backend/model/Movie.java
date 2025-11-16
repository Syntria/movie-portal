package com.egegermen.movieportal_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"watchlistEntries", "favoriteEntries", "comments", "cast"})
@ToString(callSuper = true, exclude = {"watchlistEntries", "favoriteEntries", "comments", "cast"})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Movie extends BaseEntity {

    @Column(nullable = false)
    private String originalTitle;

    @Column(length = 2000)
    private String overview;

    @Column(nullable = false)
    private String posterPath;

    @Column
    private String releaseDate;

    @Column(unique = true)
    private Integer tmdbId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "director_id")
    private Director director;

    @JsonIgnore
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<MovieCharacter> movieCharacters = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WatchlistEntry> watchlistEntries = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FavoritesEntry> favoriteEntries = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
}
