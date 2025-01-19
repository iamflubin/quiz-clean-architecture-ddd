package org.iamflubin.quiz.domain;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Session {
    private static final int BASE_POINTS = 10;

    private final UUID id;

    private final Quiz quiz;

    private Score score;

    public Session(@NonNull Quiz quiz, UUID id, Score score) {
        this.quiz = quiz;
        this.id = Objects.isNull(id) ? UUID.randomUUID() : id;
        this.score = score;
    }

    public static Session create(Quiz quiz) {
        return new Session(quiz, null, null);
    }

    public void selectAnswers(@NonNull List<List<Answer>> selectedAnswers) {
        if (!Objects.equals(selectedAnswers.size(), Quiz.QUESTION_COUNT)) {
            throw new IllegalArgumentException("Invalid number of selected answers");
        }
        for (int i = 0; i < selectedAnswers.size(); i++) {
            this.quiz.getQuestions().get(i).selectAnswers(selectedAnswers.get(i));
        }
    }

    public void calculateScore() {
        var correctAnswerCount = getCorrectAnswerCount();
        var multiplier = quiz.getDifficultyLevel().getMultiplier();
        this.score = new Score(correctAnswerCount * BASE_POINTS * multiplier);
    }


    private int getCorrectAnswerCount() {
        return (int) quiz.getQuestions().stream()
                .filter(Question::isSelectedAnswerCorrect)
                .count();
    }
}
