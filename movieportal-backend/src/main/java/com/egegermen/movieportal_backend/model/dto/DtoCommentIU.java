
package com.egegermen.movieportal_backend.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoCommentIU {
    private Long id;
    private String text;
}
