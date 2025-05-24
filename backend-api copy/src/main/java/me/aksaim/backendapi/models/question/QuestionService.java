package me.aksaim.backendapi.models.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;

	// save question
	public Question save(Question question) {
		return questionRepository.save(question);
	}
}