package org.shop.autoshoppingagent.request;

import jakarta.validation.constraints.NotBlank;

public record ApprovePurchasePlanRequest(
        @NotBlank String planHash
) {
}
