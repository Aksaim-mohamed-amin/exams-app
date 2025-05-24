package me.aksaim.backendapi.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.aksaim.backendapi.models.professor.Professor;
import me.aksaim.backendapi.models.professor.ProfessorDTO;
import me.aksaim.backendapi.models.student.Student;
import me.aksaim.backendapi.models.student.StudentDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	// Register Professor
	@PostMapping("/register/professor")
	public ResponseEntity<ProfessorDTO> registerProfessor(@Valid @RequestBody RegistrationRequest request) {
		Professor professor = authenticationService.registerProfessor(request);

		return ResponseEntity
				.created(URI.create("/api/v1/professors/" + professor.getId()))
				.body(new ProfessorDTO(professor));
	}

	// Register Student
	@PostMapping("/register/student")
	public ResponseEntity<StudentDTO> registerStudent(@Valid @RequestBody RegistrationRequest request) {
		Student student = authenticationService.registerStudent(request);

		return ResponseEntity
				.created(URI.create("/api/v1/students/" + student.getId()))
				.body(new StudentDTO(student));
	}

	// Login
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest request) {
		AuthenticationResponse authResponse = authenticationService.authenticate(request.email(), request.password());
		ResponseCookie cookie = authenticationService.generateJwtCookie(authResponse.token());

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(authResponse.user());
	}

	// Logout
	@PostMapping("/logout")
	public ResponseEntity<String> logout() {
		ResponseCookie expiredCookie = authenticationService.generateJwtCookie("");
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, expiredCookie.toString())
				.body("Logout successful");
	}
}