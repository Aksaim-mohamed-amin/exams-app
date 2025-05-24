package me.aksaim.backendapi.models.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
	// find exam by title
	Optional<Exam> findByTitle(String title);

	// find exam by title and professor id
	Optional<Exam> findByTitleAndProfessorId(String title, Long professorId);

	boolean existsByTitleAndProfessorId(String title, Long professorId);
}