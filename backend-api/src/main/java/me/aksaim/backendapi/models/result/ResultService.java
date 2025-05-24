package me.aksaim.backendapi.models.result;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultService {
	private final ResultRepository resultRepository;
}