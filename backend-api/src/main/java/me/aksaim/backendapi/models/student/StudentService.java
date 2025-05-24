package me.aksaim.backendapi.models.student;

import lombok.RequiredArgsConstructor;
import me.aksaim.backendapi.config.JwtService;
import me.aksaim.backendapi.models.user.UserService;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
	private final StudentRepository studentRepository;
	private final UserService userService;
	private final JwtService jwtService;

	// save student
	public Student save(Student student) {
		return studentRepository.save(student);
	}

	// load student by id
	public Student loadById(Long id) {
		return studentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Student not found"));
	}

	// find student by email
	public Student findByEmail(String email) {
		return studentRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("Student not found"));
	}

	// Load LoggedIn Student
	public Student loadLoggedInStudent() throws IllegalArgumentException {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return findByEmail(email);
	}

	// update first name and last name
	public Student updateFirstAndLastName(Student student, String firstName, String lastName) {
		userService.updateFirstAndLastName(student, firstName, lastName);
		return save(student);
	}

	// update email
	public Student updateEmail(Student student, String email, String password) {
		userService.updateEmail(student, email, password);
		return save(student);
	}

	// update password
	public void updatePassword(Student student, String currentPassword, String newPassword) {
		userService.updatePassword(student, currentPassword, newPassword);
	}

	// Generate token
	public String generateToken(Student student) {
		return jwtService.generateToken(student);
	}

	// Generate JWT cookie
	public ResponseCookie generateJwtCookie(String token) {
		return jwtService.generateJwtCookie(token);
	}
}