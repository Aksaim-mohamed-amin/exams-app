package me.aksaim.backendapi.models.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	// Load user by username
	@Override
	public User loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email).orElseThrow(
				() -> new UsernameNotFoundException("User not found with email: " + email)
		);
	}

	// Load user by email and password
	public User loadUserByEmailAndPassword(String email, String password) throws IllegalArgumentException {
		return userRepository.findByEmail(email)
				.filter(user -> passwordEncoder.matches(password, user.getPassword()))
				.orElseThrow(() -> new IllegalArgumentException("Bad credentials"));
	}

	// Check if user exists by email
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	// update first name and last name
	public <T extends User> void updateFirstAndLastName(T user, String firstName, String lastName) {
		user.setFirstName(firstName);
		user.setLastName(lastName);
	}

	// update email
	public <T extends User> void updateEmail(T user, String email, String password) {
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("Invalid password");
		}

		if (existsByEmail(email) && !user.getEmail().equals(email)) {
			throw new IllegalArgumentException("Email already in use");
		}

		user.setEmail(email);
	}

	// update password
	public <T extends User> void updatePassword(T user, String currentPassword, String newPassword) {
		if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
			throw new IllegalArgumentException("Invalid password");
		}

		user.setPassword(passwordEncoder.encode(newPassword));
	}
}