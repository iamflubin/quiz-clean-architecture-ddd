package org.iamflubin.quiz.application;

import lombok.RequiredArgsConstructor;
import org.iamflubin.quiz.application.spi.QuizGenerator;
import org.iamflubin.quiz.application.spi.SessionRepository;
import org.iamflubin.quiz.domain.Question;
import org.iamflubin.quiz.domain.Quiz;
import org.iamflubin.quiz.domain.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StartSessionUseCase {
    private final SessionRepository sessionRepository;
    private final QuizGenerator quizGenerator;

    @Transactional
    public Session execute(CreateSessionCommand command) {
        List<Question> questions = quizGenerator.generate(command.getCategory(),
                command.getDifficultyLevel(), Quiz.QUESTION_COUNT);

        Quiz quiz = Quiz.create(questions, command.getCategory(), command.getDifficultyLevel());

        return sessionRepository.save(Session.create(quiz));
    }
}
