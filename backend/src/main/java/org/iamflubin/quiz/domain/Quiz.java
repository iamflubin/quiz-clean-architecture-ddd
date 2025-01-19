package org.iamflubin.quiz.domain;

import lombok.Getter;
import lombok.NonNull;

import java.util.*;

@Getter
public class Quiz {
    public static final int QUESTION_COUNT = 10;

    private final UUID id;

    private final Category category;

    private final DifficultyLevel difficultyLevel;

    private final List<Question> questions;

    public Quiz(@NonNull List<Question> questions, @NonNull Category category,
                @NonNull DifficultyLevel difficultyLevel, UUID id) {
        this.questions = new ArrayList<>(questions);
        this.category = category;
        this.difficultyLevel = difficultyLevel;
        this.id = Objects.isNull(id) ? UUID.randomUUID() : id;
    }

    public static Quiz create(List<Question> questions, Category category,
                              DifficultyLevel difficultyLevel) {
        return new Quiz(questions, category, difficultyLevel, null);
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }
}
