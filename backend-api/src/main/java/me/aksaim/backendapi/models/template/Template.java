package me.aksaim.backendapi.models.template;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.aksaim.backendapi.models.professor.Professor;
import me.aksaim.backendapi.models.question.Question;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "professor_id"})})
@Getter
@Setter
@NoArgsConstructor
public class Template {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "professor_id", nullable = false)
	private Professor professor;

	@OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Question> questions = new HashSet<>();

	// Constructor
	public Template(String title, String description, Professor professor) {
		this.title = title;
		this.description = description;
		this.professor = professor;
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

	// equals and hashCode
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Template other)) return false;

		return Objects.equals(title, other.title) &&
				Objects.equals(professor != null ? professor.getId() : null,
						other.professor != null ? other.professor.getId() : null);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, professor != null ? professor.getId() : null);
	}

	// String representation
	@Override
	public String toString() {
		return "Template{" +
				"Id=" + id +
				", Title='" + title + '\'' +
				", Description='" + description + '\'' +
				", CreatedAt=" + createdAt +
				", UpdatedAt=" + updatedAt +
				", ProfessorId=" + (professor == null ? "null" : professor.getId()) +
				", QuestionsSize=" + (questions == null ? "null" : questions.size()) +
				'}';
	}
}