package me.aksaim.backendapi.models.user;

public interface UserDTO {
	Long id();

	Role role();

	String firstName();

	String lastName();

	String email();
}