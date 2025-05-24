package me.aksaim.backendapi.models.professor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {
	private final ProfessorRepository professorRepository;

	// find by email
	public Professor findByEmail(String email) {
		return professorRepository.findByEmail(email).orElse(null);
	}

	// find by id
	public Professor findById(Long id) {
		return professorRepository.findById(id).orElse(null);
	}

	// save
	public Professor save(Professor professor) {
		return professorRepository.save(professor);
	}

	// delete
	public void delete(Professor professor) {
		professorRepository.delete(professor);
	}

	// get all professors
	public List<Professor> getAllProfessors() {
		return professorRepository.findAll();
	}

	// Check if the professor is already registered using email
	public boolean existsByEmail(String email) {
		return professorRepository.existsByEmail(email);
	}
}