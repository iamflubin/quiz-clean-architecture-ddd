package org.iamflubin.quiz.domain;

import lombok.Getter;
import lombok.NonNull;

import java.util.*;

@Getter
public class Question {
    private final UUID id;

    private final String text;

    private final Set<Answer> answers;

    private final Set<Answer> correctAnswers;

    private Answer selectedAnswer;

    public Question(@NonNull String text, @NonNull Set<Answer> answers,
                    @NonNull Set<Answer> correctAnswers, UUID id, Answer selectedAnswer) {
        if (text.isBlank()) {
            throw new IllegalArgumentException("Question text cannot be blank");
        }
        this.text = text;
        this.answers = new HashSet<>(answers);
        this.correctAnswers = new HashSet<>(correctAnswers);
        this.id = Objects.isNull(id) ? UUID.randomUUID() : id;
        this.selectedAnswer = selectedAnswer;
    }


    public static Question create(String text, Set<Answer> answers, Set<Answer> correctAnswers) {
        return new Question(text, answers, correctAnswers, null, null);
    }

    public Set<Answer> getAnswers() {
        return Collections.unmodifiableSet(answers);
    }

    public Set<Answer> getCorrectAnswers() {
        return Collections.unmodifiableSet(correctAnswers);
    }
}
