package org.iamflubin.quiz.presentation.rest;

import java.util.List;
import java.util.UUID;


public record CreateSessionResponse(UUID id, List<QuestionResponse> questions) {
    public record QuestionResponse(UUID id, String text, List<String> answers) {
    }
}
