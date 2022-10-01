CREATE TABLE article(
    code VARCHAR PRIMARY KEY,
    name VARCHAR NOT NULL
);

INSERT INTO article(code, name)
SELECT generate_series(1, 100000), 'Some article';