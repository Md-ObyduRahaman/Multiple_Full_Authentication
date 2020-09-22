package com.multiple.authentication.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multiple.authentication.model.User;

public interface UserDetailsRepo extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String uname);
}
