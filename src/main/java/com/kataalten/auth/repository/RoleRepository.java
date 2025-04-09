package com.kataalten.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kataalten.auth.model.AppRole;

public interface RoleRepository extends JpaRepository<AppRole, Integer> {
	AppRole findByName(String role);
}
