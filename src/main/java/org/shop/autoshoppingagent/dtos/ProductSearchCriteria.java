package org.shop.autoshoppingagent.dtos;

import java.math.BigDecimal;

public record ProductSearchCriteria(

        String keyword,

        String category,

        String brand,

        BigDecimal maxPrice,

        Integer maxDeliveryDays

) {
}
