package com.egegermen.movieportal_backend.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoUserIU {

    private Long id;

    private String username;

    private String password;

}
