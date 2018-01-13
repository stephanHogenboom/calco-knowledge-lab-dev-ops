CREATE TABLE address (
    kix_code TEXT NOT NULL,
    country TEXT NOT NULL,
    street TEXT NOT NULL,
    house_number INTEGER NOT NULL,
    extension TEXT,
    postal_code TEXT NOT NULL,
    city TEXT NOT NULL,
    PRIMARY KEY (kix_code) ON CONFLICT IGNORE
    );