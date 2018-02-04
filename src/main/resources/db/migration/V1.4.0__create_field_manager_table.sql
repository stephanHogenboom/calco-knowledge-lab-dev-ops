CREATE TABLE field_manager (
    oid INTEGER NOT NULL,
    full_name TEXT NOT NULL,
    mobile_phone TEXT,
    email TEXT,
    PRIMARY KEY (oid) ON CONFLICT IGNORE
    );