package me.aksaim.backendapi.models.assignedExam;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.aksaim.backendapi.models.exam.Exam;
import me.aksaim.backendapi.models.exam.ExamStatus;
import me.aksaim.backendapi.models.student.Student;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class AssignedExam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime sentAt;

	@Column(nullable = false)
	private LocalDateTime dueAt;

	@Column(nullable = false)
	private ExamStatus status;

	@Column(nullable = false)
	private int attempt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_id", nullable = false)
	private Exam exam;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	public AssignedExam(Exam exam, Student student, LocalDateTime dueAt) {
		this.exam = exam;
		this.student = student;
		this.dueAt = dueAt;
		this.sentAt = LocalDateTime.now();
		this.status = ExamStatus.PENDING;
		this.attempt = 0;
	}

	@Override
	public String toString() {
		return "AssignedExam{" +
				"id=" + id +
				", sentAt=" + sentAt +
				", dueAt=" + dueAt +
				", status=" + status +
				", attempt=" + attempt +
				", exam=" + exam.getId() +
				", student=" + student.getId() +
				'}';
	}
}