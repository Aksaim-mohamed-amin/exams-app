package me.aksaim.backendapi.models.question;

import me.aksaim.backendapi.models.answer.AnswerDTO;

import java.util.Set;

public record QuestionDTO(
		String text,
		Set<AnswerDTO> answers
) {
}