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

    private final Set<Question> questions;

    public Quiz(@NonNull Set<Question> questions, @NonNull Category category,
                @NonNull DifficultyLevel difficultyLevel, UUID id) {
        this.questions = new HashSet<>(questions);
        this.category = category;
        this.difficultyLevel = difficultyLevel;
        this.id = Objects.isNull(id) ? UUID.randomUUID() : id;
    }

    public static Quiz create(Set<Question> questions, Category category,
                              DifficultyLevel difficultyLevel) {
        return new Quiz(questions, category, difficultyLevel, null);
    }

    public Set<Question> getQuestions() {
        return Collections.unmodifiableSet(questions);
    }
}
