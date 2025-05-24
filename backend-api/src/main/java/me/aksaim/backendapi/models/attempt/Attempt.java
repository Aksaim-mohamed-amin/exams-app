package me.aksaim.backendapi.models.attempt;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.aksaim.backendapi.models.exam.Exam;
import me.aksaim.backendapi.models.question.Question;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attempt {
	@Column(nullable = false)
	LocalDateTime startedAt;
	@Column(nullable = false)
	LocalDateTime submittedAt;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "exam_id", nullable = false)
	private Exam exam;

	@ManyToMany
	@JoinTable(name = "attempt_question",
			joinColumns = @JoinColumn(name = "attempt_id"),
			inverseJoinColumns = @JoinColumn(name = "question_id"))
	private Set<Question> questions = new HashSet<>();

	@ElementCollection
	@CollectionTable(name = "student_answers", joinColumns = @JoinColumn(name = "attempt_id"))
	@MapKeyColumn(name = "question_id")
	@Column(name = "answer_id")
	private Map<Long, Long> studentAnswers = new HashMap<>();

	// Constructor
	public Attempt(Exam exam, Set<Question> questions) {
		this.exam = exam;
		this.questions = questions;
		this.startedAt = LocalDateTime.now();
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
		if (!(o instanceof Attempt other)) return false;

		if (this.id != null && other.id != null) {
			return this.id.equals(other.id);
		}

		return Objects.equals(this.exam, other.exam)
				&& Objects.equals(this.questions, other.questions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	// String representation
	@Override
	public String toString() {
		return "Attempt{" +
				"Id=" + id +
				", StartedAt=" + startedAt +
				", SubmittedAt=" + submittedAt +
				", CreatedAt=" + createdAt +
				", UpdatedAt=" + updatedAt +
				", Exam=" + exam +
				", Questions=" + questions.stream().map(Question::getText).collect(Collectors.toSet()) +
				", StudentAnswers=" + studentAnswers +
				'}';
	}
}
