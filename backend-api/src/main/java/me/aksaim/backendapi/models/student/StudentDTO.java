package me.aksaim.backendapi.models.student;

import me.aksaim.backendapi.models.user.Role;
import me.aksaim.backendapi.models.user.UserDTO;

public record StudentDTO(
		Long id,
		Role role,
		String firstName,
		String lastName,
		String email
) implements UserDTO {
	// Constructor
	public StudentDTO(Student student) {
		this(
				student.getId(),
				Role.STUDENT,
				student.getFirstName(),
				student.getLastName(),
				student.getEmail()
		);
	}
}