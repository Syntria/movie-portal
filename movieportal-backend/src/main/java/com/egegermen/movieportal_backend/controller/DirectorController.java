package com.egegermen.movieportal_backend.controller;

import com.egegermen.movieportal_backend.model.dto.DtoDirectorDetails;
import com.egegermen.movieportal_backend.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/directors")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping("/{id}")
    public ResponseEntity<DtoDirectorDetails> getDirectorDetails(@PathVariable Long id) {
        return directorService.getDirectorDetailsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
