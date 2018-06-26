package com.ufc.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufc.br.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
	
}
