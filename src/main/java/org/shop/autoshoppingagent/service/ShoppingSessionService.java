package org.shop.autoshoppingagent.service;

import lombok.RequiredArgsConstructor;
import org.shop.autoshoppingagent.entity.ShoppingSession;
import org.shop.autoshoppingagent.enums.ShoppingSessionStatus;
import org.shop.autoshoppingagent.repository.ShoppingSessionRepository;
import org.shop.autoshoppingagent.request.CreateShoppingSessionRequest;
import org.shop.autoshoppingagent.response.ShoppingSessionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingSessionService {

    private final ShoppingSessionRepository repository;

    @Transactional
    public ShoppingSessionResponse create(
            CreateShoppingSessionRequest request) {

        OffsetDateTime now = OffsetDateTime.now();

        ShoppingSession session = new ShoppingSession();

        session.setId(UUID.randomUUID());
        session.setUserId(request.userId());
        session.setRequestText(request.request());
        session.setStatus(ShoppingSessionStatus.CREATED);
        session.setCreatedAt(now);
        session.setUpdatedAt(now);

        ShoppingSession saved = repository.save(session);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ShoppingSessionResponse get(UUID sessionId) {

        ShoppingSession session = repository.findById(sessionId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Shopping session not found: "
                                        + sessionId));

        return toResponse(session);
    }

    private ShoppingSessionResponse toResponse(
            ShoppingSession session) {

        return new ShoppingSessionResponse(
                session.getId(),
                session.getUserId(),
                session.getRequestText(),
                session.getStatus(),
                session.getCreatedAt()
        );
    }
}
