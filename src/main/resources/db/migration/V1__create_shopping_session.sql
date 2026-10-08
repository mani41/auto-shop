CREATE TABLE shopping_session
(
    id             UUID PRIMARY KEY,
    user_id         VARCHAR(100) NOT NULL,
    request_text    TEXT NOT NULL,
    status          VARCHAR(50) NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITH TIME ZONE NOT NULL
);