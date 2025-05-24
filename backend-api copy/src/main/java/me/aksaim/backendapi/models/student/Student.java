package me.aksaim.backendapi.models.student;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.aksaim.backendapi.models.UserRole;
import me.aksaim.backendapi.models.assignedExam.AssignedExam;
import me.aksaim.backendapi.models.result.Result;
import me.aksaim.backendapi.models.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends User {
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AssignedExam> assignedExams;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Result> results;

	public Student(String firstName, String lastName, String email, String password, List<AssignedExam> assignedExams, List<Result> results) {
		this(firstName, lastName, email, password);
		this.assignedExams = assignedExams;
		this.results = results;
	}

	public Student(String firstName, String lastName, String email, String password, List<AssignedExam> assignedExams) {
		this(firstName, lastName, email, password);
		this.assignedExams = assignedExams;
		this.results = new ArrayList<>();
	}

	public Student(String firstName, String lastName, String email, String password) {
		super(firstName, lastName, email, password, UserRole.STUDENT);
		this.assignedExams = new ArrayList<>();
		this.results = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + getId() +
				", firstName='" + getFirstName() + '\'' +
				", lastName='" + getLastName() + '\'' +
				", email='" + getEmail() + '\'' +
				'}';
	}
}