package me.aksaim.backendapi.models.professor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

	// find by email
	Optional<Professor> findByEmail(String email);
}