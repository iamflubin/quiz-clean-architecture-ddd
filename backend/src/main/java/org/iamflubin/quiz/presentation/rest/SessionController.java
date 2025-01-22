package org.iamflubin.quiz.presentation.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iamflubin.quiz.application.CalculateScoreCommand;
import org.iamflubin.quiz.application.CalculateScoreUseCase;
import org.iamflubin.quiz.application.CreateSessionCommand;
import org.iamflubin.quiz.application.StartSessionUseCase;
import org.iamflubin.quiz.domain.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/sessions")
class SessionController {
    private final StartSessionUseCase startSessionUseCase;
    private final CalculateScoreUseCase calculateScoreUseCase;


    @PostMapping
    CompletableFuture<ResponseEntity<CreateSessionResponse>> createSession
            (@Valid @RequestBody CreateSessionCommand command) {
        return CompletableFuture.supplyAsync(() -> {
            var session = startSessionUseCase.execute(command);

            var responses = session.getQuiz().getQuestions().stream()
                    .map(q -> new CreateSessionResponse
                            .QuestionResponse(q.getId(), q.getText(), q.getAnswers().stream()
                            .map(Answer::text).toList()))
                    .toList();


            return new ResponseEntity<>(new CreateSessionResponse(session.getId(), responses),
                    HttpStatus.CREATED);
        });
    }

    @PostMapping("/{id}/complete")
    ResponseEntity<CompletedSessionResponse> completeSession
            (@Valid @RequestBody CalculateScoreRequest request,
             @PathVariable(value = "id") UUID id) {

        List<List<Answer>> selectedAnswers = request.answers().stream()
                .map(innerList -> innerList.stream()
                        .map(Answer::new)
                        .toList())
                .toList();

        var session = calculateScoreUseCase
                .execute(new CalculateScoreCommand(id, selectedAnswers));

        var quiz = session.getQuiz();

        var questions = quiz.getQuestions().stream()
                .map(q -> new CompletedSessionResponse.CompletedQuestion(
                        q.getText(),
                        q.getSelectedAnswers().stream().map(Answer::text).toList(),
                        q.getCorrectAnswers().stream().map(Answer::text).toList(),
                        q.isSelectedAnswerCorrect()
                )).toList();

        return ResponseEntity.ok(new CompletedSessionResponse(session.getScore().value(),
                questions));
    }
}
