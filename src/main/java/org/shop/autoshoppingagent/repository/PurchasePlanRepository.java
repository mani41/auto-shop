package org.shop.autoshoppingagent.repository;

import org.shop.autoshoppingagent.entity.PurchasePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PurchasePlanRepository
        extends JpaRepository<PurchasePlan, UUID> {

    Optional<PurchasePlan> findByShoppingSession_Id(UUID shoppingSessionId);
}