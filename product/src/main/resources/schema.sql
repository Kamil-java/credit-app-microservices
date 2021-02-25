CREATE SCHEMA IF NOT EXISTS product_db AUTHORIZATION postgres;

-- TODO delete this if we want persist data
DROP TABLE IF EXISTS product_db.product CASCADE ;
DROP SEQUENCE IF EXISTS product_db.credit_sequence;

CREATE TABLE IF NOT EXISTS credit_db.credit (id int4 not null, credit_name TEXT not null, PRIMARY KEY (id));
CREATE SEQUENCE IF NOT EXISTS product_db.credit_sequence START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS product_db.product (credit_id int4 not null, product_name TEXT not null, value int4 not null, PRIMARY KEY (credit_id));
ALTER TABLE product_db.product ADD CONSTRAINT FK874v8exweavn7fp1c74ik3afa FOREIGN KEY (credit_id) REFERENCES credit_db.credit
