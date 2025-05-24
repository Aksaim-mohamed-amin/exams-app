package me.aksaim.backendapi.models.template;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
	// check if the template exists by title and professor id
	boolean existsByTitleAndProfessorId(String title, Long professorId);
}