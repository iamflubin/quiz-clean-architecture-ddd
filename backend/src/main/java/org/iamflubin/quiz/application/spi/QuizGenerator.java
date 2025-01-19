package org.iamflubin.quiz.application.spi;

import org.iamflubin.quiz.domain.Category;
import org.iamflubin.quiz.domain.DifficultyLevel;
import org.iamflubin.quiz.domain.Question;

import java.util.Set;

public interface QuizGenerator {
    Set<Question> generate(Category category, DifficultyLevel difficultyLevel, int questionCount);
}
