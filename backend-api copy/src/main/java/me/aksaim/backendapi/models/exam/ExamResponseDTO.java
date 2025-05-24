package me.aksaim.backendapi.models.exam;

import me.aksaim.backendapi.models.question.Question;

import java.util.List;
import java.util.Map;

public record ExamResponseDTO(
		Long id,
		String title,
		String description,
		int questionCount,
		int durationInMinutes,
		int passingScore,
		int maxAttempts,
		Map<Long, String> professor,
		List<String> questions) {

	public ExamResponseDTO(Exam exam) {
		this(
				exam.getId(),
				exam.getTitle(),
				exam.getDescription(),
				exam.getNumberOfQuestions(),
				exam.getDurationInMinutes(),
				exam.getPassingScore(),
				exam.getMaxAttempts(),
				Map.of(exam.getProfessor().getId(), exam.getProfessor().getFullName()),
				exam.getQuestions().stream().map(Question::getQuestionText).toList()
		);
	}
}