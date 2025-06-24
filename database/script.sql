--- Create sequence that auto increment for Account number and CIF number

CREATE SEQUENCE seq_account_number START 100000000 INCREMENT 1; -- 9-digit
CREATE SEQUENCE seq_cif_number     START 100000     INCREMENT 1; -- 6-digit

---------------------
--- Create table
-- table customer
CREATE TABLE public.customer (
	id serial primary key,
	cif varchar(6),
	first_name varchar(30),
	last_name varchar(30),
	gender varchar(10),
	date_of_birth timestamp,
	phone varchar(30),
	address varchar(255),
	national_id varchar(30),
	email varchar(100),
	occupation varchar(255),
	passport_number varchar(30),
	created_at timestamp,
	updated_at timestamp,
	created_by varchar(30),
	updated_by varchar(30)
);

-- table account
CREATE TABLE public.account (
	id serial primary key,
	customer_id integer NOT NULL,
	account_number varchar(9),
	account_type varchar(30),
	currency varchar(3),
	balance numeric(19, 2),
	status varchar(30),
	opened_at timestamp,
	closed_at timestamp,
	created_at timestamp,
	created_by varchar(30),
	updated_at timestamp,
	updated_by varchar(30),
	CONSTRAINT account_account_number_key UNIQUE (account_number),
	CONSTRAINT fkey_customer_account FOREIGN KEY (customer_id) REFERENCES public.customer(id)
);

-- table transaction
CREATE TABLE public."transaction" (
    id serial primary key,
	transaction_reference varchar(255),
	account_id integer NOT NULL,
	amount numeric(19, 2) ,
	currency varchar(3),
	channel varchar(30) ,
	transaction_type varchar(30) ,
	transaction_date timestamp,
	status varchar(30) ,
	purpose varchar(255) ,
	CONSTRAINT fkey_account_transaction foreign KEY (account_id) REFERENCES public.account(id)
);

---------------------