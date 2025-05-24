package me.aksaim.backendapi.models.attempt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttemptService {
	private final AttemptRepository attemptRepository;
}