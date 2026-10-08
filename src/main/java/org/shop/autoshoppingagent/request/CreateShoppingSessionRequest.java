package org.shop.autoshoppingagent.request;

import jakarta.validation.constraints.NotBlank;

public record CreateShoppingSessionRequest(

        @NotBlank(message = "User ID is required")
        String userId,

        @NotBlank(message = "Shopping request is required")
        String request

) {
}
