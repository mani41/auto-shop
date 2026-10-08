package org.shop.autoshoppingagent.dtos;

import java.math.BigDecimal;

public record ProductSearchResult(

        String externalId,

        String merchantCode,

        String merchantName,

        String name,

        String brand,

        String category,

        BigDecimal price,

        String currency,

        BigDecimal shippingCost,

        Integer deliveryDays,

        BigDecimal rating,

        boolean stockAvailable,

        String productUrl

) {
}
