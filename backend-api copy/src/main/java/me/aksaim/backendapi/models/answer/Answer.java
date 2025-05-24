package me.aksaim.backendapi.models.answer;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.aksaim.backendapi.models.question.Question;

@Entity
@Data
@NoArgsConstructor
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String answerText;

	@Column(nullable = false)
	private boolean isCorrect;

	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

	public Answer(String answerText, boolean isCorrect) {
		this.answerText = answerText;
		this.isCorrect = isCorrect;
	}
}