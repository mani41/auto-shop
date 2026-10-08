package org.shop.autoshoppingagent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.shop.autoshoppingagent.enums.ShoppingSessionStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "shopping_session")
@Getter
@Setter
public class ShoppingSession {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "request_text", nullable = false)
    private String requestText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShoppingSessionStatus status;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
