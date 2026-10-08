CREATE TABLE purchase_plan_item
(
    id                  UUID PRIMARY KEY,

    purchase_plan_id    UUID NOT NULL,

    merchant_id         UUID NOT NULL,

    product_id          UUID NOT NULL,

    external_product_id VARCHAR(200) NOT NULL,

    product_name        VARCHAR(500) NOT NULL,

    merchant_name       VARCHAR(200) NOT NULL,

    quantity            INTEGER NOT NULL,

    unit_price          NUMERIC(12, 2) NOT NULL,

    shipping_cost       NUMERIC(12, 2) NOT NULL,

    line_total          NUMERIC(12, 2) NOT NULL,

    currency            VARCHAR(10) NOT NULL,

    created_at          TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_plan_item_plan
        FOREIGN KEY (purchase_plan_id)
            REFERENCES purchase_plan(id),

    CONSTRAINT fk_plan_item_merchant
        FOREIGN KEY (merchant_id)
            REFERENCES merchant(id),

    CONSTRAINT fk_plan_item_product
        FOREIGN KEY (product_id)
            REFERENCES product(id),

    CONSTRAINT chk_plan_item_quantity
        CHECK (quantity > 0)
);

CREATE INDEX idx_purchase_plan_item_plan
    ON purchase_plan_item(purchase_plan_id);