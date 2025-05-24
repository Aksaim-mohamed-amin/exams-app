package me.aksaim.backendapi.models.question;

import lombok.RequiredArgsConstructor;
import me.aksaim.backendapi.models.answer.Answer;
import me.aksaim.backendapi.models.answer.AnswerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
	private final QuestionsRepository questionsRepository;
	private final AnswerService answerService;

	// Question has at least one correct answer
	public boolean hasAtLeastOneCorrectAnswer(Question question) {
		return question.getAnswers().stream().anyMatch(Answer::isCorrect);
	}

	// add answer to question
	public void addAnswerToQuestion(Question question, Answer answer) {
		question.getAnswers().add(answer);
		answer.setQuestion(question);
	}

	// remove answer from question
	public void removeAnswerFromQuestion(Question question, Answer answer) {
		question.getAnswers().remove(answer);
		answer.setQuestion(null);
	}

	// parse Question DTO to entity
	public Question parseDTO(QuestionDTO questionDto) {
		Question question = new Question(questionDto.text());

		if (questionDto.answers() != null) {
			questionDto.answers().forEach(answerDto -> {
				Answer answer = answerService.parseDTO(answerDto);
				addAnswerToQuestion(question, answer);
			});
		}

		return question;
	}
}