package org.iamflubin.quiz.application;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.iamflubin.common.SelfValidatable;
import org.iamflubin.quiz.domain.Answer;

import java.util.List;
import java.util.UUID;

@Getter
public class CalculateScoreCommand extends SelfValidatable {
    @NotNull
    private final UUID sessionId;
    @NotNull
    private final List<@NotNull List<Answer>> selectedAnswers;

    public CalculateScoreCommand(UUID sessionId, List<List<Answer>> selectedAnswers) {
        this.sessionId = sessionId;
        this.selectedAnswers = selectedAnswers;
        validate(this);
    }
}
