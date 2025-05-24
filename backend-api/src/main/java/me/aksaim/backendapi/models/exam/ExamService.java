package me.aksaim.backendapi.models.exam;

import lombok.RequiredArgsConstructor;
import me.aksaim.backendapi.models.student.Student;
import me.aksaim.backendapi.models.student.StudentService;
import me.aksaim.backendapi.models.template.Template;
import me.aksaim.backendapi.models.template.TemplateService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExamService {
	private final ExamRepository examRepository;
	private final TemplateService templateService;
	private final StudentService studentService;

	// assign exam to a student
	public Exam parseDTO(ExamDTO examDTO) {
		Template template = templateService.loadById(examDTO.examTemplateId());
		return new Exam(template, examDTO.numberOfQuestions(), examDTO.duration(), examDTO.maxAttempts(), examDTO.dueAt());
	}

	// assign exam to a student
	public Exam assignExamToStudent(Exam exam, Long studentId) {
		Student student = studentService.loadById(studentId);
		student.getExams().add(exam);
		exam.setStudent(student);
		exam.setStatus(ExamStatus.ASSIGNED);
		return examRepository.save(exam);
	}

	// Load logged in student exams
	public Set<Exam> loadLoggedStudentAllExams() {
		Student loggedStudent = studentService.loadLoggedInStudent();
		return loggedStudent.getExams();
	}

	// Load all exams assigned to a student
	public Set<Exam> loadExamsByStudentId(Long studentId) {
		Student student = studentService.loadById(studentId);
		return student.getExams();
	}
}