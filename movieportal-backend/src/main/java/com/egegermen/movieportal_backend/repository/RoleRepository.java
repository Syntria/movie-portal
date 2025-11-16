package com.egegermen.movieportal_backend.repository;

import com.egegermen.movieportal_backend.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {
	@Query("SELECT r FROM Role r WHERE r.name = ?1")

	Role findByName(String name);

}
