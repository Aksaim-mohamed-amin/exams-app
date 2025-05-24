package me.aksaim.backendapi.auth;

import lombok.RequiredArgsConstructor;
import me.aksaim.backendapi.config.JwtService;
import me.aksaim.backendapi.models.professor.Professor;
import me.aksaim.backendapi.models.professor.ProfessorDTO;
import me.aksaim.backendapi.models.professor.ProfessorService;
import me.aksaim.backendapi.models.student.Student;
import me.aksaim.backendapi.models.student.StudentDTO;
import me.aksaim.backendapi.models.student.StudentService;
import me.aksaim.backendapi.models.user.Role;
import me.aksaim.backendapi.models.user.User;
import me.aksaim.backendapi.models.user.UserDTO;
import me.aksaim.backendapi.models.user.UserService;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final ProfessorService professorService;
	private final StudentService studentService;

	// register professor
	public Professor registerProfessor(RegistrationRequest request) {
		if (userService.existsByEmail(request.email())) {
			throw new IllegalArgumentException("Email already in use");
		}

		String hashedPassword = passwordEncoder.encode(request.password());
		Professor professor = new Professor(request.firstName(), request.lastName(), request.email(), hashedPassword);

		return professorService.save(professor);
	}

	// register student
	public Student registerStudent(RegistrationRequest request) {
		if (userService.existsByEmail(request.email())) {
			throw new IllegalArgumentException("Email already in use");
		}

		String hashedPassword = passwordEncoder.encode(request.password());
		Student student = new Student(request.firstName(), request.lastName(), request.email(), hashedPassword);

		return studentService.save(student);
	}

	// authenticate user
	public AuthenticationResponse authenticate(String email, String password) {
		User user = userService.loadUserByEmailAndPassword(email, password);
		String token = jwtService.generateToken(user);

		UserDTO userDTO = user.getRole().equals(Role.PROFESSOR)
				? new ProfessorDTO((Professor) user)
				: new StudentDTO((Student) user);

		return new AuthenticationResponse(token, userDTO);
	}

	// generate JWT cookie
	public ResponseCookie generateJwtCookie(String token) {
		return jwtService.generateJwtCookie(token);
	}
}