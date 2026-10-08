package org.shop.autoshoppingagent.response;

import org.shop.autoshoppingagent.enums.PurchasePlanStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ApprovalResponse(
        UUID planId,
        PurchasePlanStatus status,
        BigDecimal totalAmount,
        String currency,
        String planHash,
        OffsetDateTime approvedAt
) {
}
