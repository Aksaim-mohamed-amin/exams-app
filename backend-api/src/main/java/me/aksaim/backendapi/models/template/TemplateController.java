package me.aksaim.backendapi.models.template;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/professors/exams-templates")
public class TemplateController {
	private final TemplateService templateService;

	// Get all exam templates
	@GetMapping("")
	public ResponseEntity<?> getAllExamTemplates() {
		Set<TemplateOutputDTO> templates = templateService.loadLoggedProfessorAllExamsTemplates()
				.stream()
				.map(TemplateOutputDTO::new)
				.collect(Collectors.toSet());

		return ResponseEntity.ok(templates);
	}

	@PostMapping("")
	public ResponseEntity<?> createExamTemplate(@Valid @RequestBody TemplateDTO templateDTO) {
		Template template = templateService.parseDTO(templateDTO);
		Template savedTemplate = templateService.save(template);

		return ResponseEntity
				.created(URI.create("/api/v1/professors/exams/" + savedTemplate.getId()))
				.body(new TemplateOutputDTO(savedTemplate));
	}

	// Get exam template by ID
	@GetMapping("/{id}")
	public ResponseEntity<?> getExamTemplateById(@PathVariable Long id) {
		Template template = templateService.loadById(id);
		return ResponseEntity.ok(new TemplateOutputDTO(template));
	}


	// Update exam template
	@PutMapping("/{id}")
	public ResponseEntity<?> updateExamTemplate(@PathVariable Long id, @Valid @RequestBody TemplateDTO templateDTO) {
		Template oldExamTemplate = templateService.loadById(id);
		Template newExamTemplate = templateService.parseDTO(templateDTO);
		Template updatedExamTemplate = templateService.update(oldExamTemplate, newExamTemplate);

		return ResponseEntity.ok(new TemplateOutputDTO(updatedExamTemplate));
	}

	// Delete exam template
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteExamTemplate(@PathVariable Long id) {
		Template template = templateService.loadById(id);
		templateService.delete(template);
		return ResponseEntity.noContent().build();
	}
}