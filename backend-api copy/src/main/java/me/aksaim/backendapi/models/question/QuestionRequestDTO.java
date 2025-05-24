package me.aksaim.backendapi.models.question;

import me.aksaim.backendapi.models.answer.AnswerRequestDTO;

import java.util.List;

public record QuestionRequestDTO(
		String questionText,
		List<String> tags,
		List<String> imagesUrls,
		List<AnswerRequestDTO> answers
) {
}