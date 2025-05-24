package me.aksaim.backendapi.models.user;

import jakarta.validation.constraints.NotBlank;

public record UpdateNameRequest(
		@NotBlank String firstName,
		@NotBlank String lastName
) {
}
