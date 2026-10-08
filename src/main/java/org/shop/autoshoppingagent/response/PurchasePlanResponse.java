package org.shop.autoshoppingagent.response;

import org.shop.autoshoppingagent.enums.PurchasePlanStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PurchasePlanResponse(
        UUID planId,
        UUID shoppingSessionId,
        PurchasePlanStatus status,
        UUID productId,
        String productName,
        String merchantName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal shippingCost,
        BigDecimal subtotal,
        BigDecimal totalAmount,
        String currency,
        String planHash,
        OffsetDateTime expiresAt,
        OffsetDateTime createdAt
) {}
