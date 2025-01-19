package org.iamflubin.quiz.presentation.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iamflubin.quiz.application.CreateSessionCommand;
import org.iamflubin.quiz.application.StartSessionUseCase;
import org.iamflubin.quiz.domain.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/sessions")
class SessionController {
    private final StartSessionUseCase startSessionUseCase;


    @PostMapping
    ResponseEntity<Set<QuestionResponse>> createSession
            (@Valid @RequestBody CreateSessionCommand command) {

        var quiz = startSessionUseCase.execute(command);

        var response = quiz.getQuestions().stream()
                .map(q -> new QuestionResponse(q.getId(), q.getText(), q.getAnswers().stream()
                        .map(Answer::text).collect(Collectors.toSet())))
                .collect(Collectors.toSet());


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
