package me.aksaim.backendapi.auth;

import me.aksaim.backendapi.models.user.UserDTO;

public record AuthenticationResponse(
		String token,
		UserDTO user
) {
}
