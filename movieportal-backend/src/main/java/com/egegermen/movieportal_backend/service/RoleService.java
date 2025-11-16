package com.egegermen.movieportal_backend.service;

import com.egegermen.movieportal_backend.model.Role;

public interface RoleService {
	void checkAndAddRoles();

	Role findRoleByName(String name);
}
