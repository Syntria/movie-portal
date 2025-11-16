package com.egegermen.movieportal_backend.controller;

import com.egegermen.movieportal_backend.model.dto.DtoUser;
import com.egegermen.movieportal_backend.model.dto.DtoUserIU;
import com.egegermen.movieportal_backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// Create user
	@PostMapping
	public ResponseEntity<DtoUser> createUser(@RequestBody DtoUserIU dtoUserIU) {
		DtoUser createdUser = userService.saveUser(dtoUserIU);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED); // 201 CREATED
	}

	// Update user by id
	@PutMapping("/{id}")
	public ResponseEntity<DtoUser> updateUser(@PathVariable Long id,
			@RequestBody DtoUserIU dtoUserIU) {
		DtoUser updatedUser = userService.updateUser(id, dtoUserIU);
		return ResponseEntity.ok(updatedUser);
	}

	// Get all users
	@GetMapping
	public ResponseEntity<Page<DtoUser>> getAllUsers(Pageable pageable) {
		Page<DtoUser> users = userService.findAllUsers(pageable);
		return ResponseEntity.ok(users);
	}

	// Get user by id
	@GetMapping("/{id}")
	public ResponseEntity<DtoUser> getUserById(@PathVariable Long id) {
		return userService.findUserById(id)
				.map(user -> ResponseEntity.ok(user))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/login")
	public ResponseEntity<DtoUser> login(@RequestParam String username,
			@RequestParam String password) {
		return userService.findUserByUsernameAndPassword(username, password)
				.map(user -> ResponseEntity.ok(user)) // If present, wrap in 200 OK
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()); // If empty, return
																					// 401
																					// Unauthorized
	}

	// Delete user
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build(); // 204 NO CONTENT
	}
}
