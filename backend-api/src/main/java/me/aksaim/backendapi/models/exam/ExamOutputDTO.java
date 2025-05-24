package me.aksaim.backendapi.models.exam;

import java.time.format.DateTimeFormatter;

public record ExamOutputDTO(
		Long id,
		String title,
		Integer numberOfQuestions,
		Integer duration,
		String assignedAt,
		String dueAt,
		Integer remainingAttempts,
		ExamStatus status
) {
	// Constructor
	public ExamOutputDTO(Exam exam) {
		this(
				exam.getId(),
				exam.getTemplate().getTitle(),
				exam.getNumberOfQuestions(),
				exam.getDuration(),
				exam.getAssignedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
				exam.getDueAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
				exam.getRemainingAttempts(),
				exam.getStatus()
		);
	}
}