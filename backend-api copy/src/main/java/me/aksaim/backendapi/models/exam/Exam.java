package me.aksaim.backendapi.models.exam;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.aksaim.backendapi.models.professor.Professor;
import me.aksaim.backendapi.models.question.Question;
import me.aksaim.backendapi.models.result.Result;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "professor_id"})})
@Data
@NoArgsConstructor
public class Exam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private int numberOfQuestions;

	@Column(nullable = false)
	private int durationInMinutes;

	@Column(nullable = false)
	private int passingScore;

	@Column(nullable = false)
	private int maxAttempts;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "professor_id", nullable = false)
	private Professor professor;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "exam_questions",
			joinColumns = @JoinColumn(name = "exam_id"),
			inverseJoinColumns = @JoinColumn(name = "question_id"))
	private List<Question> questions = new ArrayList<>();

	@OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Result> results;

	public Exam(String title, String description, int questionCount, int durationInMinutes, int passingScore, int maxAttempts, Professor professor, List<Question> questions) {
		this(title, description, durationInMinutes, passingScore, maxAttempts, questionCount, professor);
		this.questions = questions;
		this.results = new ArrayList<>();
	}

	public Exam(String title, String description, int numberOfQuestions, int durationInMinutes, int passingScore, int maxAttempts, Professor professor) {
		this.title = title;
		this.description = description;
		this.durationInMinutes = durationInMinutes;
		this.passingScore = passingScore;
		this.maxAttempts = maxAttempts;
		this.numberOfQuestions = numberOfQuestions;
		this.professor = professor;
		this.questions = new ArrayList<>();
		this.results = new ArrayList<>();
	}

	public boolean isReady() {
		return this.questions.size() >= this.numberOfQuestions;
	}

	@Override
	public String toString() {
		return "Exam{" +
				"id=" + id +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", numberOfQuestions=" + numberOfQuestions +
				", durationInMinutes=" + durationInMinutes +
				", passingScore=" + passingScore +
				", maxAttempts=" + maxAttempts +
				'}';
	}
}