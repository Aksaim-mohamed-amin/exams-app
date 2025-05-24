package me.aksaim.backendapi.models.professor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.aksaim.backendapi.models.exam.Exam;
import me.aksaim.backendapi.models.exam.ExamRequestDTO;
import me.aksaim.backendapi.models.exam.ExamResponseDTO;
import me.aksaim.backendapi.models.exam.ExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/professors")
public class ProfessorController {
	private final PasswordEncoder passwordEncoder;
	private final ProfessorService professorService;
	private final ExamService examService;

	// TODO: Implement the endpoints for the professor controller
	// TODO: Get Logged In Professor
	// TODO: Get a professor by id
	// TODO: Create a professor
	// TODO: Update a professor
	// TODO: Delete a professor

	// Add a professor
	@PostMapping("")
	public ResponseEntity<?> addProfessor(@Valid @RequestBody ProfessorRequestDTO requestDTO) {
		System.out.println("Adding professor: " + requestDTO);
		if (professorService.existsByEmail(requestDTO.email())) {
			return ResponseEntity.badRequest().body("This email is already registered");
		}

		Professor professor = new Professor(requestDTO.firstName(), requestDTO.lastName(), requestDTO.email(), passwordEncoder.encode(requestDTO.password()));
		ProfessorResponseDTO responseDTO = new ProfessorResponseDTO(professorService.save(professor));
		return ResponseEntity.ok(responseDTO);
	}

	// Get logged in professor
	@GetMapping("")
	public ResponseEntity<ProfessorResponseDTO> getProfessor() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Professor professor = professorService.findByEmail(email);

		if (professor == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(new ProfessorResponseDTO(professor));
	}

	// Get all professor exams
	@GetMapping("/exams")
	public ResponseEntity<List<ExamResponseDTO>> getProfessorExams() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Professor professor = professorService.findByEmail(email);

		if (professor == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(professor.getExams().stream().map(ExamResponseDTO::new).toList());
	}

	// Create an exam for the logged in professor
	@PostMapping("/exams")
	public ResponseEntity<?> createExam(@Valid @RequestBody ExamRequestDTO requestDTO) {
		Professor professor = professorService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		if (professor == null) {
			return ResponseEntity.notFound().build();
		}

		if (examService.existsByTitleAndProfessorId(requestDTO.title(), professor.getId())) {
			return ResponseEntity.badRequest().body("This exam already exists");
		}

		Exam exam = requestDTO.toEntity(professor);
		Exam savedExam = examService.save(exam);

		return ResponseEntity.ok(new ExamResponseDTO(savedExam));
	}
}