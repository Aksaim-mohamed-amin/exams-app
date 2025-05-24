package me.aksaim.backendapi.models.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students/exams")
public class ExamStudentController {
	private final ExamService examService;

	// Get all exams for logged in student
	@GetMapping("")
	public ResponseEntity<Set<ExamOutputDTO>> getAllExamsForLoggedInStudent() {
		Set<ExamOutputDTO> exams = examService.loadLoggedStudentAllExams()
				.stream()
				.map(ExamOutputDTO::new)
				.collect(Collectors.toSet());

		return ResponseEntity.ok(exams);
	}
}