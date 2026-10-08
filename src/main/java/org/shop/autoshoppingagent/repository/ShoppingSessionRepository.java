package org.shop.autoshoppingagent.repository;

import org.shop.autoshoppingagent.entity.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShoppingSessionRepository
        extends JpaRepository<ShoppingSession, UUID> {
}
