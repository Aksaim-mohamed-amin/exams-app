package me.aksaim.backendapi.models.question;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.aksaim.backendapi.models.answer.Answer;
import me.aksaim.backendapi.models.exam.Exam;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String questionText;
	@ElementCollection
	private List<String> imagesUrls = new ArrayList<>();
	@ElementCollection
	private List<String> tags = new ArrayList<>();
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Answer> answers = new ArrayList<>();

	@ManyToMany(mappedBy = "questions")
	private List<Exam> exam = new ArrayList<>();

	public Question(String questionText, List<Answer> answers, List<String> imagesUrls, List<String> tags) {
		this(questionText, answers, imagesUrls);
		this.tags = tags;
	}

	public Question(String questionText, List<Answer> answers, List<String> imagesUrls) {
		this(questionText, answers);
		this.imagesUrls = imagesUrls;
	}

	public Question(String questionText, List<Answer> answers) {
		this.questionText = questionText;
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "Question{" +
				"id=" + id +
				", questionText='" + questionText + '\'' +
				", tags=" + tags +
				'}';
	}
}