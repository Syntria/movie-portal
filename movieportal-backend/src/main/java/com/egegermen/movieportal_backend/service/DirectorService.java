package com.egegermen.movieportal_backend.service;

import com.egegermen.movieportal_backend.model.dto.DtoDirectorDetails;
import java.util.Optional;

public interface DirectorService {
	Optional<DtoDirectorDetails> getDirectorDetailsById(Long id);
}
