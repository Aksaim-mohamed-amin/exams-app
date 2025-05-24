package me.aksaim.backendapi.models.exam;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import me.aksaim.backendapi.models.answer.Answer;
import me.aksaim.backendapi.models.answer.AnswerRequestDTO;
import me.aksaim.backendapi.models.professor.Professor;
import me.aksaim.backendapi.models.question.Question;
import me.aksaim.backendapi.models.question.QuestionRequestDTO;

import java.util.List;

public record ExamRequestDTO(
		@NotBlank(message = "Title must not be blank") String title,
		@NotBlank(message = "Description must not be blank") String description,
		@Positive(message = "Number of questions must be greater than 0") int numberOfQuestions,
		@Positive(message = "Duration must be greater than 0") int durationInMinutes,
		@Positive(message = "Passing score must be greater than 0") int passingScore,
		@Positive(message = "Max attempts must be greater than 0") int maxAttempts,
		List<QuestionRequestDTO> questions
) {
	public Exam toEntity(Professor professor) {
		Exam exam = new Exam(title, description, numberOfQuestions, durationInMinutes, passingScore, maxAttempts, professor);

		if (questions != null) {
			for (QuestionRequestDTO qdto : questions) {
				Question question = new Question();
				question.setQuestionText(qdto.questionText());
				question.setTags(qdto.tags());
				question.setImagesUrls(qdto.imagesUrls());

				// Create and set answers for the question
				for (AnswerRequestDTO adto : qdto.answers()) {
					Answer answer = new Answer();
					answer.setAnswerText(adto.answerText());
					answer.setCorrect(adto.isCorrect());
					answer.setQuestion(question);
					question.getAnswers().add(answer);
				}

				// Add the question to the exam
				exam.getQuestions().add(question);
			}
		}

		System.out.println(exam);
		System.out.println(exam.getQuestions());

		return exam;
	}
}