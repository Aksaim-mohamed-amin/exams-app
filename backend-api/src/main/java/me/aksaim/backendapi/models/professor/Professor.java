package me.aksaim.backendapi.models.professor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.aksaim.backendapi.models.template.Template;
import me.aksaim.backendapi.models.user.Role;
import me.aksaim.backendapi.models.user.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Professor extends User {
	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Template> templates = new HashSet<>();

	// Constructor
	public Professor(String firstName, String lastName, String email, String password) {
		super(firstName, lastName, email, password, Role.PROFESSOR);
	}
}