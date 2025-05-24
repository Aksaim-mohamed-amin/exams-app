package me.aksaim.backendapi.models.answer;

public record AnswerProfessorDTO(
		Long id,
		String text,
		boolean isCorrect
) {
	// constructor
	public AnswerProfessorDTO(Answer answer) {
		this(
				answer.getId(),
				answer.getText(),
				answer.isCorrect()
		);
	}
}