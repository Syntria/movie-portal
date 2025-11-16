package com.egegermen.movieportal_backend.config;

import com.egegermen.movieportal_backend.service.RoleService;
import com.egegermen.movieportal_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
	private final RoleService roleService;
	private final UserService userService;

	@Autowired
	public DataLoader(RoleService roleService, UserService userService) {
		this.roleService = roleService;
		this.userService = userService;
	}

	@Override
	public void run(ApplicationArguments args) {
		roleService.checkAndAddRoles();
		userService.checkAndAddUsers();
	}
}
