package org.shop.autoshoppingagent.repository;

import org.shop.autoshoppingagent.entity.PurchasePlanItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PurchasePlanItemRepository
        extends JpaRepository<PurchasePlanItem, UUID> {

    List<PurchasePlanItem> findByPurchasePlan_Id(UUID purchasePlanId);
}
