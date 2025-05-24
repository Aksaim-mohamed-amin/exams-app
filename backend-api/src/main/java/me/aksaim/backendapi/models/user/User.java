package me.aksaim.backendapi.models.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false, unique = true)
	@Email(message = "Email should be valid")
	private String email;

	@Column(nullable = false)
	@Size(min = 6, message = "Password must be at least 6 characters long")
	private String password;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Enumerated(EnumType.STRING)
	private Role role;

	// Constructor
	public User(String firstName, String lastName, String email, String password, Role role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	// Pre persist
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	// Pre update
	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	// Get Authorities
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
	}

	// Get Username
	@Override
	public String getUsername() {
		return this.email;
	}

	// Equals and HashCode
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User user)) return false;

		if (user.getId() != null && this.id != null) {
			return user.getId().equals(this.id);
		}

		return user.getEmail().equals(this.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.email);
	}

	// String Representation of User
	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" +
				"Id=" + id +
				", FirstName='" + firstName + '\'' +
				", LastName='" + lastName + '\'' +
				", Email='" + email + '\'' +
				", CreatedAt=" + createdAt +
				", UpdatedAt=" + updatedAt +
				'}';
	}
}