package me.aksaim.backendapi.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import me.aksaim.backendapi.models.user.Role;
import me.aksaim.backendapi.models.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {
	@Value("${jwt.secret}")
	public String KEY;
	public Long EXPIRATION_TIME = TimeUnit.DAYS.toSeconds(1);

	// Generate secret key
	private SecretKey secretKey() {
		byte[] decodedKey = Base64.getDecoder().decode(KEY);
		return Keys.hmacShaKeyFor(decodedKey);
	}

	// Generate JWT token
	public String generateToken(User user) {
		return Jwts.builder()
				.subject(user.getEmail())
				.claim("role", user.getRole().name())
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusSeconds(EXPIRATION_TIME)))
				.signWith(secretKey())
				.compact();
	}

	// Generate JWT token cookie
	public ResponseCookie generateJwtCookie(String token) {
		return ResponseCookie.from("token", token)
				.httpOnly(true)
				.secure(false)
				.sameSite("Lax")
				.path("/")
				.maxAge(token.isEmpty() ? Duration.ofSeconds(0) : Duration.ofDays(1))
				.build();
	}

	// extract all claims
	public Claims parseToken(String token) {
		return Jwts.parser()
				.verifyWith(secretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	// Get user email from token
	public String extractUserEmail(String token) {
		return parseToken(token).getSubject();
	}

	// Get user role from token
	public String extractUserRole(String token) {
		return parseToken(token).get("role", String.class);
	}

	// Get expiration date from token
	public Date extractExpirationDate(String token) {
		return parseToken(token).getExpiration();
	}

	// Validate token
	public boolean validateToken(String token, User user) {
		String email = extractUserEmail(token);
		Date expirationDate = extractExpirationDate(token);
		Role role = Role.valueOf(extractUserRole(token));

		return email.equals(user.getEmail())
				&& expirationDate.after(Date.from(Instant.now()))
				&& role.equals(user.getRole());
	}
}