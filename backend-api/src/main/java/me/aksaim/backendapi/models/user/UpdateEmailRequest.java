package me.aksaim.backendapi.models.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateEmailRequest(
		@NotBlank @Email String email,
		@NotBlank String password
) {
}