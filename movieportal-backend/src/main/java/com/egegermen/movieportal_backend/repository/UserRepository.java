package com.egegermen.movieportal_backend.repository;

import com.egegermen.movieportal_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	Optional<User> findByUsername(String username);
}

