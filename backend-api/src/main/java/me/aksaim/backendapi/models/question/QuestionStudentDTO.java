package me.aksaim.backendapi.models.question;

import me.aksaim.backendapi.models.answer.AnswerStudentDTO;

import java.util.Set;

public record QuestionStudentDTO(
		Long id,
		String text,
		Set<AnswerStudentDTO> answers
) {
	// Constructor
	public QuestionStudentDTO(Question question) {
		this(
				question.getId(),
				question.getText(),
				question.getAnswers().stream()
						.map(AnswerStudentDTO::new)
						.collect(java.util.stream.Collectors.toSet())
		);
	}
}
