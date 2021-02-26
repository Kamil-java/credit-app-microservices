CREATE SCHEMA IF NOT EXISTS credit_db;

-- delete this if we want persist data
DROP TABLE IF EXISTS credit_db.credit CASCADE;
DROP SEQUENCE IF EXISTS credit_db.credit_sequence;

CREATE SEQUENCE IF NOT EXISTS credit_db.credit_sequence START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS credit_db.credit (id int4 not null, credit_name TEXT not null, PRIMARY KEY (id))