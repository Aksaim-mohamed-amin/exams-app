package me.aksaim.backendapi.models.professor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.aksaim.backendapi.models.UserRole;
import me.aksaim.backendapi.models.exam.Exam;
import me.aksaim.backendapi.models.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Professor extends User {
	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Exam> exams = new ArrayList<>();

	public Professor(String firstName, String lastName, String email, String password) {
		super(firstName, lastName, email, password, UserRole.PROFESSOR);
	}

	@Override
	public String toString() {
		return "Professor{" +
				"id=" + getId() +
				", firstName='" + getFirstName() + '\'' +
				", lastName='" + getLastName() + '\'' +
				", email='" + getEmail() + '\'' +
				'}';
	}
}