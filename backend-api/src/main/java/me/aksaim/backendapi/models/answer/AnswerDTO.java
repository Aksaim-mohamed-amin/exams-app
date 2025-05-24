package me.aksaim.backendapi.models.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnswerDTO(
		@NotBlank String text,
		@NotNull boolean isCorrect
) {
}