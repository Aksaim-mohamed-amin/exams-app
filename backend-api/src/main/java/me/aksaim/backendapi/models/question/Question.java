package me.aksaim.backendapi.models.question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.aksaim.backendapi.models.answer.Answer;
import me.aksaim.backendapi.models.template.Template;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String text;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Answer> answers = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "template_id", nullable = false)
	private Template template;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	// Constructor
	public Question(String text) {
		this.text = text;
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
		if (!(o instanceof Question question)) return false;

		if (id != null && question.id != null) {
			return id.equals(question.id);
		}

		return text.equals(question.text) && answers.equals(question.answers);
	}

	@Override
	public int hashCode() {
		return Objects.hash(text, answers);
	}

	// String representation
	@Override
	public String toString() {
		return "Question{" +
				"Id=" + id +
				", Text='" + text + '\'' +
				", CreatedAt=" + createdAt +
				", UpdatedAt=" + updatedAt +
				", Answers=" + (answers.stream().map(Answer::getText)).collect(Collectors.toSet()) +
				'}';
	}
}