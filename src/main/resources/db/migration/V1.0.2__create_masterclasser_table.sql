CREATE TABLE master_classer (
    oid INTEGER NOT NULL,
    full_name TEXT NOT NULL,
    address_id TEXT NOT NULL,
    start_date TEXT NOT NULL,
    end_date TEXT,
    job_type INTEGER,
    telephone TEXT,
    email TEXT,
    company_id INTEGER,
    field_manager_id INTEGER,
    PRIMARY KEY (full_name),
    FOREIGN KEY(company_id) REFERENCES company(oid) ON DELETE RESTRICT,
    FOREIGN KEY(address_id) REFERENCES address(kix_code) ON DELETE RESTRICT,
    FOREIGN KEY(field_manager_id) REFERENCES field_manager(oid) ON DELETE RESTRICT,
    UNIQUE (full_name),
    UNIQUE (telephone),
    UNIQUE (email)
    );