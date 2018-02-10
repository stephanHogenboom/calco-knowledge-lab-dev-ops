CREATE TABLE specialization (
    oid INTEGER NOT NULL,
    name TEXT NOT NULL,
    PRIMARY KEY (oid) ON CONFLICT IGNORE
    );