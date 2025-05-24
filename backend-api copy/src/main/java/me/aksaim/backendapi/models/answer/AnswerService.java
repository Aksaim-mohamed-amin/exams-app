package me.aksaim.backendapi.models.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;

	// save an answer
	public Answer save(Answer answer) {
		return answerRepository.save(answer);
	}
}