package me.aksaim.backendapi.models.exam;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ExamDTO(
		@NotNull @Positive Long studentId,
		@NotNull @Positive Long examTemplateId,
		@NotNull @Positive Integer numberOfQuestions,
		@NotNull @Positive Integer duration,
		@NotNull @Positive Integer maxAttempts,
		@NotNull @Future LocalDateTime dueAt
) {
}