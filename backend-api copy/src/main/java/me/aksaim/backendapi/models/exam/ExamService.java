package me.aksaim.backendapi.models.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {
	private final ExamRepository examRepository;

	// find exam by id
	public Exam findById(Long id) {
		return examRepository.findById(id).orElse(null);
	}

	// find exam by title
	public Exam findByTitle(String title) {
		return examRepository.findByTitle(title).orElse(null);
	}

	// find exam by title and professor id
	public Exam findByTitleAndProfessorId(String title, Long professorId) {
		return examRepository.findByTitleAndProfessorId(title, professorId).orElse(null);
	}

	// Check if exam exists by title and professor id
	public boolean existsByTitleAndProfessorId(String title, Long professorId) {
		return examRepository.existsByTitleAndProfessorId(title, professorId);
	}

	// save exam
	public Exam save(Exam exam) {
		return examRepository.save(exam);
	}

	// delete exam
	public void delete(Exam exam) {
		examRepository.delete(exam);
	}

	// find all exams
	public List<Exam> findAll() {
		return examRepository.findAll();
	}
}
