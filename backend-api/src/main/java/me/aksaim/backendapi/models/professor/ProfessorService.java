package me.aksaim.backendapi.models.professor;

import lombok.RequiredArgsConstructor;
import me.aksaim.backendapi.config.JwtService;
import me.aksaim.backendapi.models.user.UserService;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorService {
	private final ProfessorRepository professorRepository;
	private final UserService userService;
	private final JwtService jwtService;

	// save professor
	public Professor save(Professor professor) {
		return professorRepository.save(professor);
	}

	// find professor by email
	public Professor findByEmail(String email) {
		return professorRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Professor not found"));
	}

	// Load LoggedIn Professor
	public Professor loadLoggedInProfessor() throws UsernameNotFoundException {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return findByEmail(email);
	}

	// update first name and last name
	public Professor updateFirstAndLastName(Professor professor, String firstName, String lastName) {
		userService.updateFirstAndLastName(professor, firstName, lastName);
		return save(professor);
	}

	// update email
	public Professor updateEmail(Professor professor, String email, String password) {
		userService.updateEmail(professor, email, password);
		return save(professor);
	}

	// update password
	public void updatePassword(Professor professor, String currentPassword, String newPassword) {
		userService.updatePassword(professor, currentPassword, newPassword);
	}

	// Generate token
	public String generateToken(Professor professor) {
		return jwtService.generateToken(professor);
	}

	// Generate JWT cookie
	public ResponseCookie generateJwtCookie(String token) {
		return jwtService.generateJwtCookie(token);
	}
}