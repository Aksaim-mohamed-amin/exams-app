package me.aksaim.backendapi.models.answer;

public record AnswerStudentDTO(
		Long id,
		String text
) {
	// constructor
	public AnswerStudentDTO(Answer answer) {
		this(
				answer.getId(),
				answer.getText()
		);
	}
}