CREATE SEQUENCE IF NOT EXISTS hibernate_sequence
    START WITH 5
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS "client"
(
    "id"   bigint PRIMARY KEY,
    "name" varchar(60) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS "to_rent"
(
    "id"          bigint PRIMARY KEY,
    "name"        varchar(120) UNIQUE NOT NULL,
    "price"       NUMERIC(19, 2)      NOT NULL,
    "area"        DOUBLE PRECISION    NOT NULL,
    "description" varchar(240)        NOT NULL
);

CREATE TABLE IF NOT EXISTS "reservation"
(
    "id"              bigint PRIMARY KEY,
    "start_rent_date" date           NOT NULL,
    "end_rent_date"   date           NOT NULL,
    "cost"            NUMERIC(19, 2) NOT NULL,
    "tenant_id"       bigint,
    "landlord_id"     bigint,
    "to_rent_id"      bigint,
    CONSTRAINT fk_tenant
        FOREIGN KEY ("tenant_id")
            REFERENCES "client" ("id"),
    CONSTRAINT fk_landlord
        FOREIGN KEY ("landlord_id")
            REFERENCES "client" ("id"),
    CONSTRAINT fk_to_rent
        FOREIGN KEY ("to_rent_id")
            REFERENCES "to_rent" ("id")
);