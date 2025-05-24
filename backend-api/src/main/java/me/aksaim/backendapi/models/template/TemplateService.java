package me.aksaim.backendapi.models.template;

import lombok.RequiredArgsConstructor;
import me.aksaim.backendapi.models.professor.Professor;
import me.aksaim.backendapi.models.professor.ProfessorService;
import me.aksaim.backendapi.models.question.Question;
import me.aksaim.backendapi.models.question.QuestionService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TemplateService {
	private final TemplateRepository templateRepository;
	private final ProfessorService professorService;
	private final QuestionService questionService;

	// save template
	public Template save(Template template) {
		return templateRepository.save(template);
	}

	// check if the template exists by title and professor id
	public boolean existsByTitleAndProfessorId(String title, Long professorId) {
		return templateRepository.existsByTitleAndProfessorId(title, professorId);
	}

	// Load by id
	public Template loadById(Long id) {
		return templateRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Exam template not found with id: " + id));
	}

	// Add question to template
	public void addQuestionToTemplate(Template template, Question question) {
		if (!questionService.hasAtLeastOneCorrectAnswer(question)) {
			throw new IllegalArgumentException("Question must have at least one correct answer: '" + question.getText() + "'");
		}

		template.getQuestions().add(question);
		question.setTemplate(template);
	}

	// Remove question from template
	public void removeQuestionFromTemplate(Template template, Question question) {
		template.getQuestions().remove(question);
		question.setTemplate(null);
	}

	// parse DTO to entity
	public Template parseDTO(TemplateDTO templateDto) {
		Professor professor = professorService.loadLoggedInProfessor();

		if (existsByTitleAndProfessorId(templateDto.title(), professor.getId())) {
			throw new IllegalArgumentException("Exam with this title already exists");
		}

		Template template = new Template(templateDto.title(), templateDto.description(), professor);
		if (templateDto.questions() != null) {
			templateDto.questions().forEach(questionDto -> {
				Question question = questionService.parseDTO(questionDto);
				addQuestionToTemplate(template, question);
			});
		}

		return template;
	}

	// Update template
	public Template update(Template oldTemplate, Template newTemplate) {
		if (!newTemplate.getTitle().equalsIgnoreCase(oldTemplate.getTitle())
				&& existsByTitleAndProfessorId(newTemplate.getTitle(), oldTemplate.getProfessor().getId())) {
			throw new IllegalArgumentException("Exam with this title already exists");
		}

		oldTemplate.setTitle(newTemplate.getTitle());
		oldTemplate.setDescription(newTemplate.getDescription());

		oldTemplate.getQuestions().clear();
		newTemplate.getQuestions().forEach(question -> {
			addQuestionToTemplate(oldTemplate, question);
		});

		return templateRepository.save(oldTemplate);
	}

	// Delete template
	public void delete(Template template) {
		Set<Question> questions = new HashSet<>(template.getQuestions());
		questions.forEach(question -> {
			removeQuestionFromTemplate(template, question);
		});
		templateRepository.delete(template);
	}

	// Get professor all exam templates
	public Set<Template> loadLoggedProfessorAllExamsTemplates() {
		Professor professor = professorService.loadLoggedInProfessor();
		return professor.getTemplates();
	}
}