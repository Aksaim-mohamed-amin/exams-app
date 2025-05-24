package me.aksaim.backendapi.models.student;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.aksaim.backendapi.models.exam.Exam;
import me.aksaim.backendapi.models.result.Result;
import me.aksaim.backendapi.models.user.Role;
import me.aksaim.backendapi.models.user.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student extends User {
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private Set<Exam> exams = new HashSet<>();

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private Set<Result> results = new HashSet<>();

	// Constructor
	public Student(String firstName, String lastName, String email, String password) {
		super(firstName, lastName, email, password, Role.STUDENT);
	}
}