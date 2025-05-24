package me.aksaim.backendapi.models.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;

	// parse Answer DTO to entity
	public Answer parseDTO(AnswerDTO answerDto) {
		return new Answer(answerDto.text(), answerDto.isCorrect());
	}
}