package com.egegermen.movieportal_backend.service.impl;

import com.egegermen.movieportal_backend.component.mapper.DirectorMapper;
import com.egegermen.movieportal_backend.model.dto.DtoDirectorDetails;
import com.egegermen.movieportal_backend.repository.DirectorRepository;
import com.egegermen.movieportal_backend.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<DtoDirectorDetails> getDirectorDetailsById(Long id) {
        return directorRepository.findById(id)
                .map(DirectorMapper::toDetailsDto);
    }
}
