package com.ndembe.imagerepo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ndembe.imagerepo.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
 
}
