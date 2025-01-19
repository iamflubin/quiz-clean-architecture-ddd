package org.iamflubin.quiz.domain;

import lombok.Getter;
import lombok.NonNull;

import java.util.*;

@Getter
public class Question {
    private final UUID id;

    private final String text;

    private final List<Answer> answers;

    private final List<Answer> correctAnswers;

    private final List<Answer> selectedAnswers;

    public Question(@NonNull String text, @NonNull List<Answer> answers,
                    @NonNull List<Answer> correctAnswers, List<Answer> selectedAnswer, UUID id) {
        if (text.isBlank()) {
            throw new IllegalArgumentException("Question text cannot be blank");
        }
        this.text = text;
        this.answers = new ArrayList<>(answers);
        this.correctAnswers = new ArrayList<>(correctAnswers);
        this.id = Objects.isNull(id) ? UUID.randomUUID() : id;
        this.selectedAnswers = new ArrayList<>(selectedAnswer);
    }


    public static Question create(String text, List<Answer> answers, List<Answer> correctAnswers) {
        return new Question(text, answers, correctAnswers, Collections.emptyList(), null);
    }

    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }

    public List<Answer> getCorrectAnswers() {
        return Collections.unmodifiableList(correctAnswers);
    }

    public void selectAnswers(List<Answer> selectedAnswers) {
        if (!this.selectedAnswers.isEmpty()) {
            throw new IllegalArgumentException("User already selected answers");
        }
        this.selectedAnswers.addAll(selectedAnswers);
    }

    public boolean isSelectedAnswerCorrect() {
        return new HashSet<>(selectedAnswers).containsAll(correctAnswers);
    }
}
