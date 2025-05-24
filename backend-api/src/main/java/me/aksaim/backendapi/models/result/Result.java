package me.aksaim.backendapi.models.result;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.aksaim.backendapi.models.attempt.Attempt;
import me.aksaim.backendapi.models.student.Student;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Result {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Attempt attempt;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@Column(nullable = false)
	private Double score;

	@Column(nullable = false)
	private Grade grade;

	// Constructor
	public Result(Attempt attempt, Double score, Grade grade) {
		this.attempt = attempt;
		this.score = score;
		this.grade = grade;
		this.student = attempt != null ? attempt.getExam().getStudent() : null;
	}

	// equals and hashCode
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Result result)) return false;
		return Double.compare(result.score, score) == 0 && id.equals(result.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, score);
	}

	// String representation
	@Override
	public String toString() {
		return "Result{" +
				"id=" + id +
				", Attempt=" + attempt +
				", Score=" + score +
				", Grade=" + grade +
				'}';
	}
}