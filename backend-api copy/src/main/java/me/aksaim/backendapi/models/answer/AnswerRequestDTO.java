package me.aksaim.backendapi.models.answer;

public record AnswerRequestDTO(
		String answerText,
		boolean isCorrect
) {
}