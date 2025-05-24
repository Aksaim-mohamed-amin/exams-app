package me.aksaim.backendapi.models.exam;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.aksaim.backendapi.models.attempt.Attempt;
import me.aksaim.backendapi.models.student.Student;
import me.aksaim.backendapi.models.template.Template;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Exam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime assignedAt;

	@Column(nullable = false)
	private LocalDateTime dueAt;

	@Column(nullable = false)
	private Integer duration;

	@Column(nullable = false)
	private Integer numberOfQuestions;

	@Column(nullable = false)
	private Integer remainingAttempts;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Column(nullable = false)
	private ExamStatus status;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@ManyToOne
	@JoinColumn(name = "template_id", nullable = false)
	private Template template;

	@OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Attempt> attempts = new HashSet<>();

	// Constructor
	public Exam(Template template, Integer numberOfQuestions, Integer duration, Integer remainingAttempts, LocalDateTime dueAt) {
		this.template = template;
		this.numberOfQuestions = numberOfQuestions;
		this.duration = duration;
		this.remainingAttempts = remainingAttempts;
		this.dueAt = dueAt;
		this.assignedAt = LocalDateTime.now();
		this.status = ExamStatus.CREATED;
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
		if (!(o instanceof Exam other)) return false;

		if (this.id != null && other.id != null) {
			return Objects.equals(this.id, other.id);
		}

		Long thisStudentId = this.student != null ? this.student.getId() : null;
		Long otherStudentId = other.student != null ? other.student.getId() : null;

		Long thisTemplateId = this.template != null ? this.template.getId() : null;
		Long otherTemplateId = other.template != null ? other.template.getId() : null;

		return Objects.equals(thisStudentId, otherStudentId) &&
				Objects.equals(thisTemplateId, otherTemplateId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				this.id,
				student != null ? student.getId() : null,
				template != null ? template.getId() : null
		);
	}

	// String representation
	@Override
	public String toString() {
		return "Exam{" +
				"Id=" + id +
				", AssignedAt=" + assignedAt +
				", DueAt=" + dueAt +
				", Duration=" + duration +
				", NumberOfQuestions=" + numberOfQuestions +
				", RemainingAttempts=" + remainingAttempts +
				", CreatedAt=" + createdAt +
				", UpdatedAt=" + updatedAt +
				", StudentId=" + (student != null ? student.getId() : "null") +
				", TemplateId=" + (template != null ? template.getId() : "null") +
				'}';
	}
}