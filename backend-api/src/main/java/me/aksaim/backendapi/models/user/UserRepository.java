package me.aksaim.backendapi.models.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// find user by email
	Optional<User> findByEmail(String email);

	// Check if email exists
	boolean existsByEmail(String email);
}