package me.aksaim.backendapi;

import me.aksaim.backendapi.models.professor.Professor;
import me.aksaim.backendapi.models.student.Student;
import me.aksaim.backendapi.models.user.Role;
import me.aksaim.backendapi.models.user.User;
import org.junit.jupiter.api.Test;

public class UsersTest {
	@Test
	public void testUserCreation() {
		// create new User
		User user = new User("John", "Doe", "email@gmail.com", "password", Role.STUDENT);
		User user2 = new User("John", "Doe", "email2@gmail.com", "password", Role.PROFESSOR);

		// check if the user is created successfully
		System.out.println("User: " + user);
		System.out.println("User2: " + user2);
		System.out.println(user.equals(user2));
	}

	@Test
	public void testProfessorCreation() {
		Professor professor = new Professor("Jane", "Doe", "email3@gmail.com", "password");
		System.out.println("Professor: " + professor);
	}

	@Test
	public void testStudentCreation() {
		Student student = new Student("Jane", "Doe", "email4@gmail.com", "password");
		System.out.println("student: " + student);
	}
}