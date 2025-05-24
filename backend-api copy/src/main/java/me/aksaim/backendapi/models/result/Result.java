package me.aksaim.backendapi.models.result;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.aksaim.backendapi.models.exam.Exam;
import me.aksaim.backendapi.models.student.Student;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Result {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime submittedAt;

	@Column(nullable = false)
	private double score;

	@Column(nullable = false)
	private ResultStatus status = ResultStatus.NOT_SUBMITTED;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_id")
	private Exam exam;

	public Result(Exam exam, Student student, double score) {
		this.exam = exam;
		this.student = student;
		this.submittedAt = LocalDateTime.now();
		this.score = score;
		this.status = score >= exam.getPassingScore() ? ResultStatus.PASSED : ResultStatus.FAILED;
	}

	@Override
	public String toString() {
		return "Result{" +
				"id=" + id +
				", submittedAt=" + submittedAt +
				", score=" + score +
				", status=" + status +
				", student=" + student.getId() +
				", exam=" + exam.getId() +
				'}';
	}
}