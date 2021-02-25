CREATE SCHEMA IF NOT EXISTS customer_db AUTHORIZATION postgres;

-- TODO delete this if we want persist data
DROP TABLE IF EXISTS customer_db.customer CASCADE;
DROP SEQUENCE IF EXISTS customer_db.credit_sequence;

CREATE TABLE IF NOT EXISTS credit_db.credit (id int4 not null, credit_name TEXT not null, PRIMARY KEY (id));
CREATE SEQUENCE IF NOT EXISTS customer_db.credit_sequence start 1 increment 1;
CREATE TABLE IF NOT EXISTS customer_db.customer (credit_id int4 not null, first_name TEXT not null, pesel TEXT not null, surname TEXT not null, PRIMARY KEY (credit_id));
ALTER TABLE customer_db.customer ADD CONSTRAINT customer_pesel_unique UNIQUE (pesel);
ALTER TABLE customer_db.customer ADD CONSTRAINT FK8gv68y8f5fs3ywgaqvg2wwvg FOREIGN KEY (credit_id) REFERENCES credit_db.credit