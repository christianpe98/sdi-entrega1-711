package com.uniovi.service;

import org.springframework.stereotype.Service;

@Service
public class RolesService {
	String[] roles = { "ROLE_NORMAL", "ROLE_ADMIN" };

	public String[] getRoles() {
		return roles;
	}

}
