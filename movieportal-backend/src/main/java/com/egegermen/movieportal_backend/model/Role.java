package com.egegermen.movieportal_backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Entity
@Data
@NoArgsConstructor
public class Role extends BaseEntity implements GrantedAuthority {

    @Column
    private String name;

    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }
}
