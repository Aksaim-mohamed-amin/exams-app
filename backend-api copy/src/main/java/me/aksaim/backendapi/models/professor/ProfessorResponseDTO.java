package me.aksaim.backendapi.models.professor;

import me.aksaim.backendapi.models.exam.Exam;

import java.util.Map;
import java.util.stream.Collectors;

public record ProfessorResponseDTO(Long id, String firstName, String lastName, String email, Map<Long, String> exams) {
	public ProfessorResponseDTO(Professor professor) {
		this(
				professor.getId(),
				professor.getFirstName(),
				professor.getLastName(),
				professor.getEmail(),
				professor.getExams().stream().collect(Collectors.toMap(Exam::getId, Exam::getTitle))

		);
	}
}