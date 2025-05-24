package me.aksaim.backendapi.models.professor;

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
@RequestMapping("/api/v1/professors")
public class ProfessorController {
	private final ProfessorService professorService;

	// Get professor details
	@GetMapping("")
	public ResponseEntity<ProfessorDTO> getProfessor() {
		Professor professor = professorService.loadLoggedInProfessor();
		return ResponseEntity.ok(new ProfessorDTO(professor));
	}

	// Update insensitive information
	@PutMapping("")
	public ResponseEntity<ProfessorDTO> updateInfo(@Valid @RequestBody UpdateNameRequest request) {
		Professor professor = professorService.loadLoggedInProfessor();
		Professor updatedProfessor = professorService.updateFirstAndLastName(professor, request.firstName(), request.lastName());

		return ResponseEntity.ok(new ProfessorDTO(updatedProfessor));
	}

	// Update email
	@PutMapping("/email")
	public ResponseEntity<ProfessorDTO> updateEmail(@Valid @RequestBody UpdateEmailRequest request) {
		Professor professor = professorService.loadLoggedInProfessor();
		Professor updatedProfessor = professorService.updateEmail(professor, request.email(), request.password());

		String token = professorService.generateToken(updatedProfessor);
		ResponseCookie cookie = professorService.generateJwtCookie(token);

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new ProfessorDTO(updatedProfessor));
	}

	// update password
	@PutMapping("/password")
	public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
		Professor professor = professorService.loadLoggedInProfessor();
		professorService.updatePassword(professor, request.currentPassword(), request.newPassword());

		ResponseCookie cookie = professorService.generateJwtCookie("");
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body("Password updated successfully");
	}
}