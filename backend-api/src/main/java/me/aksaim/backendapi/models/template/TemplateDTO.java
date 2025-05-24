package me.aksaim.backendapi.models.template;

import jakarta.validation.constraints.NotBlank;
import me.aksaim.backendapi.models.question.QuestionDTO;

import java.util.Set;

public record TemplateDTO(
		@NotBlank(message = "Title is required") String title,
		@NotBlank(message = "Description is required") String description,
		Set<QuestionDTO> questions
) {
}