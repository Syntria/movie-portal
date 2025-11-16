package com.egegermen.movieportal_backend.service.impl;

import com.egegermen.movieportal_backend.component.mapper.MovieMapper;
import com.egegermen.movieportal_backend.exception.MovieNotFoundException;
import com.egegermen.movieportal_backend.model.Actor;
import com.egegermen.movieportal_backend.model.Director;
import com.egegermen.movieportal_backend.model.Movie;
import com.egegermen.movieportal_backend.model.MovieCharacter;
import com.egegermen.movieportal_backend.model.dto.DtoMovie;
import com.egegermen.movieportal_backend.model.dto.DtoMovieIU;
import com.egegermen.movieportal_backend.model.dto.tmdb.TmdbMovieCreditsDto;
import com.egegermen.movieportal_backend.model.dto.tmdb.TmdbMovieDetailsDto;
import com.egegermen.movieportal_backend.repository.ActorRepository;
import com.egegermen.movieportal_backend.repository.DirectorRepository;
import com.egegermen.movieportal_backend.repository.MovieRepository;
import com.egegermen.movieportal_backend.repository.specification.MovieSpecification;
import com.egegermen.movieportal_backend.service.MovieService;
import com.egegermen.movieportal_backend.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

	private final MovieRepository movieRepository;
	private final DirectorRepository directorRepository;
	private final ActorRepository actorRepository;
	private final TmdbService tmdbService;
	private final MovieMapper movieMapper;

@Override
@Transactional
public DtoMovie addMovieFromTmdb(int tmdbId) {
    TmdbMovieDetailsDto details = tmdbService.getMovieDetails(tmdbId);
    TmdbMovieCreditsDto credits = tmdbService.getMovieCredits(tmdbId);

    Director director = credits.getCrew().stream()
            .filter(c -> "Director".equals(c.getJob()))
            .findFirst()
            .map(crewMember -> directorRepository.findByTmdbId(crewMember.getId())
                    .orElseGet(() -> directorRepository.save(
                            Director.builder()
                                    .tmdbId(crewMember.getId())
                                    .name(crewMember.getName())
                                    .profilePath(crewMember.getProfilePath())
                                    .build()
                    )))
            .orElse(null);

    Movie movie = Movie.builder()
            .tmdbId(details.getId())
            .originalTitle(details.getTitle())
            .overview(details.getOverview())
            .posterPath(details.getPosterPath())
            .releaseDate(details.getReleaseDate())
            .director(director)
            .build();

    credits.getCast().stream()
            .limit(15)
            .forEach(castMember -> {

                Actor actor = actorRepository.findByTmdbId(castMember.getId())
                        .orElseGet(() -> actorRepository.save(
                                Actor.builder()
                                        .tmdbId(castMember.getId())
                                        .name(castMember.getName())
                                        .profilePath(castMember.getProfilePath())
                                        .build()
                        ));

                MovieCharacter mc = MovieCharacter.builder()
                        .movie(movie)
                        .actor(actor)
                        .characterName(castMember.getCharacter())
                        .build();

                movie.getMovieCharacters().add(mc);
            });

	Movie savedMovie = movieRepository.save(movie);

        savedMovie.getMovieCharacters().forEach(mc -> {
            Actor actor = mc.getActor();
            actor.getMovieCharacters().add(mc);
        });

        return movieMapper.toDto(savedMovie);
}

	@Override
	@Transactional
	public DtoMovie saveMovie(DtoMovieIU movieDto) {
		Movie movieToSave = MovieMapper.toEntity(movieDto);
		Movie savedMovie = movieRepository.save(movieToSave);
		return MovieMapper.toDto(savedMovie);
	}

	@Override
	@Transactional
	public Optional<DtoMovie> updateMovie(Long id, DtoMovieIU movieDto) {
		return movieRepository.findById(id)
				.map(existingMovie -> {
					existingMovie.setOriginalTitle(movieDto.getOriginalTitle());
					existingMovie.setOverview(movieDto.getOverview());
					existingMovie.setPosterPath(movieDto.getPosterPath());
					existingMovie.setReleaseDate(movieDto.getReleaseDate());
					Movie updatedMovie = movieRepository.save(existingMovie);
					return MovieMapper.toDto(updatedMovie);
				});
	}

	@Override
	@Transactional(readOnly = true)
	public Page<DtoMovie> getAllMovies(Pageable pageable) {
		Page<Movie> moviePage = movieRepository.findAll(pageable);
		return moviePage.map(MovieMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<DtoMovie> searchMovies(String title, Integer year, String yearFilterType, Pageable pageable) {
		// Create the dynamic specification
		Specification<Movie> spec = MovieSpecification.withDynamicQuery(title, year, yearFilterType);

		// Execute the specification with pagination
		Page<Movie> moviePage = movieRepository.findAll(spec, pageable);

		return moviePage.map(MovieMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<DtoMovie> getMovieById(Long id) {
		return movieRepository.findById(id).map(MovieMapper::toDto);
	}

	@Override
	@Transactional
	public void deleteMovie(Long id) {
		if (!movieRepository.existsById(id)) {
			throw new MovieNotFoundException(id);
		}
		movieRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Integer> getAllTmdbIds() {
		return movieRepository.findAllTmdbIds();
	}

	@Override
	@Transactional
	public void deleteMovieByTmdbId(int tmdbId) {
		movieRepository.deleteByTmdbId(tmdbId);
	}
}

