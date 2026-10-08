package org.shop.autoshoppingagent.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreatePurchasePlanRequest(
        @NotNull UUID shoppingSessionId,
        @NotNull UUID productId,
        @Min(1) int quantity
) {}