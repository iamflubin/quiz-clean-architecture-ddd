package org.iamflubin.quiz.infrastructure.persistence;

import org.iamflubin.quiz.application.spi.SessionRepository;
import org.iamflubin.quiz.domain.Session;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
class InMemorySessionRepository implements SessionRepository {
    private final Map<UUID, Session> sessions = new ConcurrentHashMap<>();

    @Override
    public Session save(Session session) {
        sessions.put(session.getId(), session);
        return sessions.get(session.getId());
    }
}
