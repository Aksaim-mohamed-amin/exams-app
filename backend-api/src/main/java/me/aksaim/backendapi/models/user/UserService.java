package me.aksaim.backendapi.models.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;

	// Find user by email
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	// Load user by username
	@Override
	public User loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email).orElseThrow(
				() -> new UsernameNotFoundException("User not found with email: " + email)
		);
	}

	// Save user
	public void save(User user) {
		userRepository.save(user);
	}

	// Delete user
	public void delete(User user) {
		userRepository.delete(user);
	}

	// Gett all users
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}