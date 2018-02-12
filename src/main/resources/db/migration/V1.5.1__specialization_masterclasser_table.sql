CREATE TABLE specialization_masterclasser (
    specialization_id INTEGER NOT NULL,
    master_classer_id INTEGER NOT NULL,
    FOREIGN KEY(specialization_id) REFERENCES specialization(oid) ON DELETE RESTRICT,
    FOREIGN KEY(master_classer_id) REFERENCES master_classer(oid) ON DELETE RESTRICT,
    PRIMARY KEY (specialization_id, master_classer_id) ON CONFLICT IGNORE
    );