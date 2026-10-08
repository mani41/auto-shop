package org.shop.autoshoppingagent.response;

import org.shop.autoshoppingagent.enums.ShoppingSessionStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ShoppingSessionResponse(

        UUID sessionId,

        String userId,

        String request,

        ShoppingSessionStatus status,

        OffsetDateTime createdAt

) {
}
