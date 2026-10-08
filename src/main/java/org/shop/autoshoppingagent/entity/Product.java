package org.shop.autoshoppingagent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(nullable = false, length = 500)
    private String name;

    private String brand;

    private String category;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private String currency;

    @Column(name = "shipping_cost", nullable = false,
            precision = 12, scale = 2)
    private BigDecimal shippingCost;

    @Column(name = "delivery_days")
    private Integer deliveryDays;

    @Column(precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(name = "stock_available", nullable = false)
    private boolean stockAvailable;

    @Column(name = "product_url")
    private String productUrl;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
