CREATE TABLE purchase_plan
(
    id                  UUID PRIMARY KEY,
    shopping_session_id UUID NOT NULL,

    status              VARCHAR(50) NOT NULL,

    currency            VARCHAR(10) NOT NULL,

    subtotal            NUMERIC(12, 2) NOT NULL,
    shipping_cost       NUMERIC(12, 2) NOT NULL,
    total_amount        NUMERIC(12, 2) NOT NULL,

    plan_hash           VARCHAR(64) NOT NULL,

    expires_at          TIMESTAMP WITH TIME ZONE,

    created_at          TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_purchase_plan_session
        FOREIGN KEY (shopping_session_id)
            REFERENCES shopping_session(id),

    CONSTRAINT uk_purchase_plan_session
        UNIQUE (shopping_session_id)
);

CREATE INDEX idx_purchase_plan_hash
    ON purchase_plan(plan_hash);

CREATE INDEX idx_purchase_plan_status
    ON purchase_plan(status);