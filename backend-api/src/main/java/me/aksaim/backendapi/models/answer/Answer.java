package me.aksaim.backendapi.models.answer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.aksaim.backendapi.models.question.Question;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String text;

	@Column(nullable = false)
	private boolean isCorrect;

	@ManyToOne
	@JoinColumn(name = "question_id", nullable = false)
	private Question question;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	// Constructor
	public Answer(String text, Boolean isCorrect) {
		this.text = text;
		this.isCorrect = isCorrect;
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
		if (!(o instanceof Answer answer)) return false;

		if (id != null && answer.id != null) {
			return id.equals(answer.id);
		}

		return text.equals(answer.text) && isCorrect == answer.isCorrect;
	}

	@Override
	public int hashCode() {
		return Objects.hash(text, isCorrect);
	}

	// String representation
	@Override
	public String toString() {
		return "Answer{" +
				"Id=" + id +
				", Text='" + text + '\'' +
				", IsCorrect=" + isCorrect +
				", QuestionId=" + (question != null ? question.getId() : null) +
				", CreatedAt=" + createdAt +
				", UpdatedAt=" + updatedAt +
				'}';
	}
}