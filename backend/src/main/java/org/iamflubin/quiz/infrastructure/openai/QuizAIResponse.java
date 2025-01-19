package org.iamflubin.quiz.infrastructure.openai;

import java.util.Set;


record QuizAIResponse(Set<QuestionAIResponse> questions) {

    record QuestionAIResponse(String text, Set<String> answers, Set<String> correctAnswers) {
    }
}
