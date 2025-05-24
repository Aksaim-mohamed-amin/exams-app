package me.aksaim.backendapi.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import me.aksaim.backendapi.models.UserRole;
import me.aksaim.backendapi.models.user.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {
	public static final String Key = "3252E7A0A96128FD68EE5DD0868B5FABA8AFA3BDC70D477BA25794A063255BF1CAF1900E2076915BA1F02FAA2961256680457BB25DECE8456893A7FBF8CDE050";
	public static final Long EXPIRATION_TIME = TimeUnit.DAYS.toSeconds(1);

	// Generate secret key
	private SecretKey secretKey() {
		byte[] decodedKey = Base64.getDecoder().decode(Key);
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
		UserRole role = UserRole.valueOf(extractUserRole(token));

		return email.equals(user.getEmail())
				&& expirationDate.after(Date.from(Instant.now()))
				&& role.equals(user.getRole());
	}
}