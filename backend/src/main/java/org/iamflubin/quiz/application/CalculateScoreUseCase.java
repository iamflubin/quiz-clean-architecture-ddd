package org.iamflubin.quiz.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iamflubin.quiz.application.spi.SessionRepository;
import org.iamflubin.quiz.domain.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculateScoreUseCase {
    private final SessionRepository sessionRepository;

    @Transactional
    public Session execute(CalculateScoreCommand command) {
        Session session = sessionRepository.findById(command.getSessionId())
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        session.selectAnswers(command.getSelectedAnswers());
        session.calculateScore();

        log.info("Score calculated. [session={}, answers={}, score={}]",
                session,
                command.getSelectedAnswers(),
                session.getScore().value());

        return sessionRepository.save(session);
    }
}
