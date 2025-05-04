package me.aksaim.backendapi.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class AuthenticationRequest {
	private String email;
	private String password;
}