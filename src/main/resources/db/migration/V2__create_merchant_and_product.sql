CREATE TABLE merchant
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    code        VARCHAR(50) NOT NULL UNIQUE,
    active      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE product
(
    id              UUID PRIMARY KEY,

    merchant_id     UUID NOT NULL,

    external_id     VARCHAR(200) NOT NULL,

    name            VARCHAR(500) NOT NULL,

    brand           VARCHAR(100),

    category        VARCHAR(100),

    price           NUMERIC(12, 2) NOT NULL,

    currency        VARCHAR(10) NOT NULL,

    shipping_cost   NUMERIC(12, 2) NOT NULL DEFAULT 0,

    delivery_days   INTEGER,

    rating          NUMERIC(3, 2),

    stock_available BOOLEAN NOT NULL DEFAULT TRUE,

    product_url     TEXT,

    created_at      TIMESTAMP WITH TIME ZONE NOT NULL,

    updated_at      TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_product_merchant
        FOREIGN KEY (merchant_id)
            REFERENCES merchant(id),

    CONSTRAINT uk_product_merchant_external
        UNIQUE (merchant_id, external_id)
);

CREATE INDEX idx_product_merchant
    ON product(merchant_id);

CREATE INDEX idx_product_category
    ON product(category);

CREATE INDEX idx_product_brand
    ON product(brand);

CREATE INDEX idx_product_price
    ON product(price);