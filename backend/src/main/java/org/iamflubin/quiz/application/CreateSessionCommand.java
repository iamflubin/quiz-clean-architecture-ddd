package org.iamflubin.quiz.application;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.iamflubin.common.SelfValidatable;
import org.iamflubin.quiz.domain.Category;
import org.iamflubin.quiz.domain.DifficultyLevel;

@Getter
public class CreateSessionCommand extends SelfValidatable {
    @NotNull
    private final DifficultyLevel difficultyLevel;
    @NotNull
    private final Category category;

    public CreateSessionCommand(DifficultyLevel difficultyLevel, Category category) {
        this.difficultyLevel = difficultyLevel;
        this.category = category;
        validate(this);
    }
}
