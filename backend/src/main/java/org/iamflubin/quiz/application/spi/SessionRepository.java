package org.iamflubin.quiz.application.spi;

import org.iamflubin.quiz.domain.Session;

public interface SessionRepository {
    Session save(Session session);
}
