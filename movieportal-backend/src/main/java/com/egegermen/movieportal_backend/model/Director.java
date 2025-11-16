package com.egegermen.movieportal_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true, exclude = "movies")
@ToString(callSuper = true, exclude = "movies")
@NoArgsConstructor
@AllArgsConstructor 
@SuperBuilder(toBuilder = true) 
@Entity
public class Director extends Crew {

    @JsonIgnore //To break infinite loop
    @OneToMany(mappedBy = "director")
    private Set<Movie> movies = new HashSet<>();
}
