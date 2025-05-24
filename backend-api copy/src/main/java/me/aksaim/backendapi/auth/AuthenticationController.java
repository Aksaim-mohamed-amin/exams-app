package me.aksaim.backendapi.auth;

import lombok.RequiredArgsConstructor;
import me.aksaim.backendapi.models.user.User;
import me.aksaim.backendapi.models.user.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
	private final UserService userService;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;

	// Login Endpoint
	@PostMapping("login")
	public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request) {
		User user = userService.findByEmail(request.getEmail());
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid email or password");
		}

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid email or password");
		}

		String jwtToken = jwtService.generateToken(user);

		ResponseCookie cookie = buildCookies(jwtToken, TimeUnit.DAYS.toSeconds(1));

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body("Authentication successful");
	}

	// Logout Endpoint
	@PostMapping("logout")
	public ResponseEntity<Object> logout() {
		ResponseCookie cookie = buildCookies("", 0);

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body("Logout successful");
	}

	// Utility Method for Token Response
	private ResponseCookie buildCookies(String token, long maxAge) {
		return ResponseCookie.from("token", token)
				.httpOnly(true)
				.secure(false)
				.sameSite("Lax")
				.path("/")
				.maxAge(maxAge)
				.build();
	}
}