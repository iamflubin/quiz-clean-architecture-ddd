package org.iamflubin.quiz.infrastructure.openai;

import java.util.List;


record QuizAIResponse(List<QuestionAIResponse> questions) {

    record QuestionAIResponse(String text, List<String> answers, List<String> correctAnswers) {
    }
}
