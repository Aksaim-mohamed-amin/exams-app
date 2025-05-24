package me.aksaim.backendapi.models.student;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.aksaim.backendapi.models.user.UpdateEmailRequest;
import me.aksaim.backendapi.models.user.UpdateNameRequest;
import me.aksaim.backendapi.models.user.UpdatePasswordRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {
	private final StudentService studentService;

	// Get student details
	@GetMapping("")
	public ResponseEntity<StudentDTO> getStudent() {
		Student student = studentService.loadLoggedInStudent();
		return ResponseEntity.ok(new StudentDTO(student));
	}

	// Update insensitive information
	@PutMapping("")
	public ResponseEntity<StudentDTO> updateInfo(@Valid @RequestBody UpdateNameRequest request) {
		Student student = studentService.loadLoggedInStudent();
		Student updatedStudent = studentService.updateFirstAndLastName(student, request.firstName(), request.lastName());

		return ResponseEntity.ok(new StudentDTO(updatedStudent));
	}

	// Update email
	@PutMapping("/email")
	public ResponseEntity<StudentDTO> updateEmail(@Valid @RequestBody UpdateEmailRequest request) {
		Student student = studentService.loadLoggedInStudent();
		Student updatedStudent = studentService.updateEmail(student, request.email(), request.password());

		String token = studentService.generateToken(updatedStudent);
		ResponseCookie cookie = studentService.generateJwtCookie(token);

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new StudentDTO(updatedStudent));
	}

	// update password
	@PutMapping("/password")
	public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
		Student student = studentService.loadLoggedInStudent();
		studentService.updatePassword(student, request.currentPassword(), request.newPassword());

		ResponseCookie cookie = studentService.generateJwtCookie("");
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body("Password updated successfully");
	}
}