package org.iamflubin.quiz.presentation.rest;

import java.util.List;


record CompletedSessionResponse(double score, List<CompletedQuestion> questions) {

    record CompletedQuestion(String text, List<String> answers, List<String> correctAnswers,
                             boolean correct) {
    }
}
