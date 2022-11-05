-- CREATE SCHEMA online_bank;

CREATE TABLE online_bank.account (
    id bigint NOT NULL PRIMARY KEY,
    sort_code CHAR(8) NOT NULL,
    account_number CHAR(8) NOT NULL,
    current_balance NUMERIC(10,3) NOT NULL,
    bank_name VARCHAR(50) NOT NULL,
    owner_name VARCHAR(50) NOT NULL,
    UNIQUE (sort_code, account_number)
);
INSERT INTO online_bank.account (id, sort_code, account_number, current_balance, bank_name, owner_name)
VALUES (1, '53-68-92', '73084635', 1071.78, 'Challenger Bank', 'Paul Dragoslav');
INSERT INTO online_bank.account (id, sort_code, account_number, current_balance, bank_name, owner_name)
VALUES (2, '65-93-37', '21956204', 67051.01, 'High Street Bank', 'Scrooge McDuck');


CREATE TABLE online_bank.transaction (
    id bigint NOT NULL PRIMARY KEY,
    source_account_id bigint NOT NULL REFERENCES online_bank.account(id),
    target_account_id bigint NOT NULL REFERENCES online_bank.account(id),
    -- Partially denormalize for performance
    target_owner_name varchar(50) NOT NULL,
    amount NUMERIC(10,3) NOT NULL,
    initiation_date timestamp NOT NULL,
    completion_date TIMESTAMP,
    reference VARCHAR(255),
    latitude REAL,
    longitude REAL
);

INSERT INTO online_bank.transaction (id, source_account_id, target_account_id, target_owner_name, amount, initiation_date, completion_date, reference)
VALUES (1, 1, 2, 'Scrooge McDuck', 100.00, '2019-04-01 10:30', '2019-04-01 10:54', 'Protection charge Apr');
INSERT INTO online_bank.transaction (id, source_account_id, target_account_id, target_owner_name, amount, initiation_date, completion_date, reference)
VALUES (2, 1, 2, 'Scrooge McDuck', 100.00, '2019-05-01 10:30', '2019-05-01 11:21', 'Protection charge May');

INSERT INTO online_bank.transaction (id, source_account_id, target_account_id, target_owner_name, amount, initiation_date, completion_date, reference)
VALUES (3, 2, 1, 'Paul Dragoslav', 10000.00, '2019-05-27 17:21', null, 'Ha Ha I am rich');

