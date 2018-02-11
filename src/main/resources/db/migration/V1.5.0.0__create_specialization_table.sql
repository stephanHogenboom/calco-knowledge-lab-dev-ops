CREATE TABLE specialization (
    oid INTEGER NOT NULL,
    course_name TEXT NOT NULL,
    PRIMARY KEY (oid) ON CONFLICT IGNORE
    );