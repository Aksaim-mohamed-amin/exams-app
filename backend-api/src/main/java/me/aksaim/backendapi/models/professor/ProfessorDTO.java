package me.aksaim.backendapi.models.professor;

import me.aksaim.backendapi.models.user.Role;
import me.aksaim.backendapi.models.user.UserDTO;

public record ProfessorDTO(
		Long id,
		Role role,
		String firstName,
		String lastName,
		String email
) implements UserDTO {
	// Constructor
	public ProfessorDTO(Professor professor) {
		this(
				professor.getId(),
				Role.PROFESSOR,
				professor.getFirstName(),
				professor.getLastName(),
				professor.getEmail()
		);
	}
}