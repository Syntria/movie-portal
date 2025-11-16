package com.egegermen.movieportal_backend.service.impl;

import com.egegermen.movieportal_backend.component.PasswordHasher;
import com.egegermen.movieportal_backend.component.mapper.UserMapper;
import com.egegermen.movieportal_backend.exception.UserNotFoundException;
import com.egegermen.movieportal_backend.model.Role;
import com.egegermen.movieportal_backend.model.SystemRole;
import com.egegermen.movieportal_backend.model.User;
import com.egegermen.movieportal_backend.model.dto.DtoUser;
import com.egegermen.movieportal_backend.model.dto.DtoUserIU;
import com.egegermen.movieportal_backend.repository.RoleRepository;
import com.egegermen.movieportal_backend.repository.UserRepository;
import com.egegermen.movieportal_backend.service.RoleService;
import com.egegermen.movieportal_backend.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final RoleService roleService;
	private final PasswordHasher passwordHasher;

	@Override
	public DtoUser saveUser(DtoUserIU dtoUserIU) {
        Role userRole = roleRepository.findByName(SystemRole.USER.name());

        User newUser = new User();
        newUser.setUsername(dtoUserIU.getUsername());
        newUser.setPassword(passwordHasher.hashPassword(dtoUserIU.getPassword()));
        
        newUser.setRoles(Collections.singletonList(userRole));
        
        User savedUser = userRepository.save(newUser);
        return convertToDto(savedUser);
	}

	@Override
	public Page<DtoUser> findAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable)
				.map(this::convertToDto);
	}

	@Override
	public Optional<DtoUser> findUserById(Long id) {
		return userRepository.findById(id).map(this::convertToDto);
	}

	@Override
	@Transactional
	public DtoUser updateUser(Long id, DtoUserIU dtoUserIU) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(id));

		existingUser.setUsername(dtoUserIU.getUsername());

		if (dtoUserIU.getPassword() != null && !dtoUserIU.getPassword().isBlank()) {
			existingUser.setPassword(passwordHasher.hashPassword(dtoUserIU.getPassword()));
		}

		User updatedUser = userRepository.save(existingUser);

		return UserMapper.toDto(updatedUser);
	}

	@Override
	@Transactional
	public Optional<DtoUser> findUserByUsername(String username) {
		return userRepository.findByUsername(username)
				.map(this::convertToDto);
	}

	@Override
	public Optional<DtoUser> findUserByUsernameAndPassword(String username, String password) {
		return userRepository.findByUsername(username)
				.filter(user -> passwordHasher.matches(password, user.getPassword()))
				.map(this::convertToDto);
	}

	@Override
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new UserNotFoundException(id);
		}
		userRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void checkAndAddUsers() {
		addDefaultUserIfNotExists("admin", "password", SystemRole.ADMIN);
		addDefaultUserIfNotExists("Ege", "123", SystemRole.USER);
	}

	private void addDefaultUserIfNotExists(String username, String rawPassword, SystemRole role) {
		if (userRepository.findByUsername(username).isEmpty()) {
			User user = new User();
			user.setUsername(username);
			user.setPassword(passwordHasher.hashPassword(rawPassword));
			user.getRoles().add(roleService.findRoleByName(role.name()));
			userRepository.save(user);
		}
	}

	private DtoUser convertToDto(User user) {
		DtoUser dto = new DtoUser();
		BeanUtils.copyProperties(user, dto);
		return dto;
	}

}
