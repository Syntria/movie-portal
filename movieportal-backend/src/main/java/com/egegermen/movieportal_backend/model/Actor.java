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
public class Actor extends Crew {


    @JsonIgnore
    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<MovieCharacter> movieCharacters = new HashSet<>();
}
