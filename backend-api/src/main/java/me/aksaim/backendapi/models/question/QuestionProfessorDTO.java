package me.aksaim.backendapi.models.question;

import me.aksaim.backendapi.models.answer.AnswerProfessorDTO;

import java.util.Set;
import java.util.stream.Collectors;

public record QuestionProfessorDTO(
		Long id,
		String text,
		Set<AnswerProfessorDTO> answers
) {
	// Constructor
	public QuestionProfessorDTO(Question question) {
		this(
				question.getId(),
				question.getText(),
				question.getAnswers().stream()
						.map(AnswerProfessorDTO::new)
						.collect(Collectors.toSet())
		);
	}
}
