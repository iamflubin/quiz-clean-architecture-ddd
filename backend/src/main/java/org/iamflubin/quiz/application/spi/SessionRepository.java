package org.iamflubin.quiz.application.spi;

import org.iamflubin.quiz.domain.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository {
    Session save(Session session);

    Optional<Session> findById(UUID id);
}
