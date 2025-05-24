package me.aksaim.backendapi.models.template;

import me.aksaim.backendapi.models.question.QuestionProfessorDTO;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

public record TemplateOutputDTO(
		Long id,
		String title,
		String description,
		String createdAt,
		String updatedAt,
		Set<QuestionProfessorDTO> questions
) {
	// Constructor
	public TemplateOutputDTO(Template template) {
		this(
				template.getId(),
				template.getTitle(),
				template.getDescription(),
				template.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
				template.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
				template.getQuestions().stream()
						.map(QuestionProfessorDTO::new)
						.collect(Collectors.toSet())
		);
	}
}