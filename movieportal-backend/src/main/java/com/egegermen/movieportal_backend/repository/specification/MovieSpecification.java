package com.egegermen.movieportal_backend.repository.specification;

import com.egegermen.movieportal_backend.model.Movie;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class MovieSpecification {

	public static Specification<Movie> withDynamicQuery(String title, Integer year, String yearFilterType) {
		return (Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			List<Predicate> predicates = new ArrayList<>();

			if (title != null && !title.isBlank()) {
				predicates.add(cb.like(cb.lower(root.get("originalTitle")), "%" + title.toLowerCase() + "%"));
			}

			if (year != null && yearFilterType != null && !yearFilterType.isBlank()) {

				if ("lessThan".equals(yearFilterType)) {
					// This creates a condition like: releaseDate < 'YYYY-01-01'
					predicates.add(cb.lessThan(root.get("releaseDate"), year.toString() + "-01-01"));
				} else if ("greaterThan".equals(yearFilterType)) {
					// This creates a condition like: releaseDate > 'YYYY-12-31'
					predicates.add(cb.greaterThan(root.get("releaseDate"), year.toString() + "-12-31"));
				}
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
