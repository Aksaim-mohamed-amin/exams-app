package me.aksaim.backendapi.models.exam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/professors/exams")
public class ExamProfessorController {
	private final ExamService examService;

	// assign exam to a student
	@PostMapping("")
	public ResponseEntity<?> assignExamToStudent(@Valid @RequestBody ExamDTO examDTO) {
		Exam exam = examService.parseDTO(examDTO);
		Exam assignedExam = examService.assignExamToStudent(exam, examDTO.studentId());

		return ResponseEntity
				.created(URI.create("/api/v1/students/exams/" + assignedExam.getId()))
				.body(new ExamOutputDTO(assignedExam));
	}

	// Get all exams assigned to a student
	@GetMapping("/students/{studentId}")
	public ResponseEntity<?> getAllExamsAssignedToStudent(@PathVariable Long studentId) {
		Set<ExamOutputDTO> exams = examService.loadExamsByStudentId(studentId)
				.stream()
				.map(ExamOutputDTO::new)
				.collect(Collectors.toSet());

		return ResponseEntity.ok(exams);
	}
}