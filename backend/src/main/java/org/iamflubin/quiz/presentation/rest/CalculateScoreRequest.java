package org.iamflubin.quiz.presentation.rest;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CalculateScoreRequest(@NotNull List<@NotNull List<String>> answers) {

}
