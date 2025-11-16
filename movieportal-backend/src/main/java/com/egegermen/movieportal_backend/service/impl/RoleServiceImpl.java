package com.egegermen.movieportal_backend.service.impl;

import com.egegermen.movieportal_backend.model.Role;
import com.egegermen.movieportal_backend.model.SystemRole;
import com.egegermen.movieportal_backend.repository.RoleRepository;
import com.egegermen.movieportal_backend.service.RoleService;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class RoleServiceImpl implements RoleService {
	private final RoleRepository roleRepository;

	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional
	public void checkAndAddRoles() {
		for (var systemRole : SystemRole.values()) {
			if (roleRepository.findByName(systemRole.name()) != null) {
				continue;
			}
			var role = Role.builder().name(systemRole.name()).build();
			roleRepository.save(role);
		}
	}

	@Override
	public Role findRoleByName(String name) {
		return roleRepository.findByName(name);
	}
}
