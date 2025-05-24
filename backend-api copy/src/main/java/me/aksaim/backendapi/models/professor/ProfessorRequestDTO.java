package me.aksaim.backendapi.models.professor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfessorRequestDTO(
		@NotBlank String firstName,
		@NotBlank String lastName,
		@NotBlank @Email String email,
		@Size(min = 8, message = "Password must be at least 8 characters long") String password
) {
}