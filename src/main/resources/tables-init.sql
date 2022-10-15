-- Table: store_employees.tbl_employee

-- DROP TABLE IF EXISTS store_employees.tbl_employee;

CREATE TABLE IF NOT EXISTS store_employees.tbl_employee
(
    id bigint NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    mobile character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    role_id bigint,
    CONSTRAINT tbl_employee_pkey PRIMARY KEY (id),
    CONSTRAINT fkqyhp9ytkc0o8uapy1vtqmw350 FOREIGN KEY (role_id)
    REFERENCES store_employees.tbl_employee_role (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS store_employees.tbl_employee
    OWNER to postgres;