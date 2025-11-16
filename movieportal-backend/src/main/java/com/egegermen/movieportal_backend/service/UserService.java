package com.egegermen.movieportal_backend.service;

import com.egegermen.movieportal_backend.model.dto.DtoUser;
import com.egegermen.movieportal_backend.model.dto.DtoUserIU;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

	DtoUser saveUser(DtoUserIU dtoUserIU);

	DtoUser updateUser(Long id, DtoUserIU dtoUser);

	Page<DtoUser> findAllUsers(Pageable pageable);

	Optional<DtoUser> findUserById(Long id);

	Optional<DtoUser> findUserByUsername(String username);

	Optional<DtoUser> findUserByUsernameAndPassword(String username, String password);

	// Put this inside the dataloader
	void checkAndAddUsers();

	void deleteUser(Long id);
}
