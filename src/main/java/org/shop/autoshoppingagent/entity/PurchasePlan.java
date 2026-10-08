package org.shop.autoshoppingagent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.shop.autoshoppingagent.enums.PurchasePlanStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchase_plan")
@Getter
@Setter
public class PurchasePlan {

    @Id
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "shopping_session_id",
            nullable = false,
            unique = true
    )
    private ShoppingSession shoppingSession;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchasePlanStatus status;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "shipping_cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal shippingCost;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "plan_hash", nullable = false, length = 64)
    private String planHash;

    @Column(name = "expires_at")
    private OffsetDateTime expiresAt;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "approved_at")
    private OffsetDateTime approvedAt;
}
