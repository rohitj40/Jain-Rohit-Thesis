DROP TABLE IF EXISTS private_data_authorization;
DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS manufacturer_data;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS producers;

CREATE TABLE IF NOT EXISTS producers(
	producer_id serial PRIMARY KEY,
	username varchar UNIQUE,
	first_name varchar NOT NULL,
	surname varchar,
	email varchar NOT NULL UNIQUE,
	token_id varchar,
	enabled boolean NOT NULL,
	token_creation_date_time timestamp,
	account_non_expired boolean NOT NULL,
	account_non_locked boolean NOT NULL,
	credentials_non_expired boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities (
    producer_id integer NOT NULL,
    authority varchar(50) NOT NULL,
    CONSTRAINT fk_authorities_producers
        FOREIGN KEY(producer_id)
    	REFERENCES producers(producer_id)
);


CREATE TABLE IF NOT EXISTS manufacturer_data(
	manufacturer_data_id serial PRIMARY KEY,
	producer_username varchar NOT NULL,
	data_field_name varchar NOT NULL,
	data_field_value varchar NOT NULL,
	is_common_data boolean,
	source_file varchar NOT NULL,
	upload_date_time timestamp,
	record_start_date_time timestamp,
	record_end_date_time timestamp,
	CONSTRAINT fk_common_data_producers
        FOREIGN KEY(producer_username)
        REFERENCES producers(username)
);


CREATE TABLE IF NOT EXISTS private_data_authorization(
    private_data_access_id serial PRIMARY KEY,
    granted_producer_username varchar NOT NULL,
    owning_producer_username varchar NOT NULL,
    manufacturer_data_field_name varchar NOT NULL,
    record_start_date_time timestamp,
    record_end_date_time timestamp,
    CONSTRAINT fk_private_data_access_granted_producer
        FOREIGN KEY(granted_producer_username)
        REFERENCES producers(username),
    CONSTRAINT fk_private_data_access_owning_producer
        FOREIGN KEY(owning_producer_username)
        REFERENCES producers(username)
);

CREATE TABLE IF NOT EXISTS message(
    message_id serial PRIMARY KEY,
    asked_by_producer_username varchar NOT NULL,
    asked_to_producer_username varchar NOT NULL,
    message_body varchar(2000) NOT NULL,
    message_subject varchar NOT NULL,
    message_reply varchar(2000),
    message_sent_date_time timestamp NOT NULL,
    reply_sent_date_time timestamp,
    is_message_sent boolean NOT NULL,
    is_message_replied boolean NOT NULL,
    CONSTRAINT fk_message_asked_by_producer
            FOREIGN KEY(asked_by_producer_username)
            REFERENCES producers(username),
    CONSTRAINT fk_message_asked_to_producer
                FOREIGN KEY(asked_to_producer_username)
                REFERENCES producers(username)
);


DELETE FROM manufacturer_data;
DELETE FROM producers;


INSERT INTO producers(producer_id, username, first_name, surname, email, token_id, enabled, token_creation_date_time, account_non_expired, account_non_locked, credentials_non_expired) VALUES(nextval('producers_producer_id_seq'), 'PD1', 'Producer', 'One', 'prod1@hotmail.com', null, true, null, true, true, true);
INSERT INTO producers(producer_id, username, first_name, surname, email, token_id, enabled, token_creation_date_time, account_non_expired, account_non_locked, credentials_non_expired) VALUES(nextval('producers_producer_id_seq'), 'PD2', 'Producer', 'Two', 'prod2@hotmail.com', null, true, null, true, true, true);
INSERT INTO producers(producer_id, username, first_name, surname, email, token_id, enabled, token_creation_date_time, account_non_expired, account_non_locked, credentials_non_expired) VALUES(nextval('producers_producer_id_seq'), 'PD3', 'Producer', 'Three', 'prod3@hotmail.com', null, true, null, true, true, true);
INSERT INTO producers(producer_id, username, first_name, surname, email, token_id, enabled, token_creation_date_time, account_non_expired, account_non_locked, credentials_non_expired) VALUES(nextval('producers_producer_id_seq'), 'PD4', 'Producer', 'Four', 'prod4@hotmail.com', null, true, null, true, true, true);


--INSERT INTO manufacturer_data(manufacturer_data_id, producer_username, data_field_name, data_field_value, is_common_data, source_file, upload_date_time, record_start_date_time, record_end_date_time) VALUES(nextval('manufacturer_data_manufacturer_data_id_seq'), 'PD1', 'Distributor ID', 'D1', false, 'file_1', '2023-09-06 00:00:01.000001', '2023-09-06 00:00:01.000001', '9999-01-01 00:00:01.000001');
--INSERT INTO manufacturer_data(manufacturer_data_id, producer_username, data_field_name, data_field_value, is_common_data, source_file, upload_date_time, record_start_date_time, record_end_date_time) VALUES(nextval('manufacturer_data_manufacturer_data_id_seq'), 'PD1', 'Name', 'DMK Deutsches Milchkontor GmbH.', true, 'file_1', '2023-09-06 00:00:01.000001', '2023-09-06 00:00:01.000001', '9999-01-01 00:00:01.000001');
--INSERT INTO manufacturer_data(manufacturer_data_id, producer_username, data_field_name, data_field_value, is_common_data, source_file, upload_date_time, record_start_date_time, record_end_date_time) VALUES(nextval('manufacturer_data_manufacturer_data_id_seq'), 'PD1', 'Delivery Date', '22-08-2023', true, 'file_1', '2023-09-06 00:00:01.000001', '2023-09-06 00:00:01.000001', '9999-01-01 00:00:01.000001');
--INSERT INTO manufacturer_data(manufacturer_data_id, producer_username, data_field_name, data_field_value, is_common_data, source_file, upload_date_time, record_start_date_time, record_end_date_time) VALUES(nextval('manufacturer_data_manufacturer_data_id_seq'), 'PD1', 'Milk Volume (liters)', '1000', true, 'file_1', '2023-09-06 00:00:01.000001', '2023-09-06 00:00:01.000001', '9999-01-01 00:00:01.000001');
--INSERT INTO manufacturer_data(manufacturer_data_id, producer_username, data_field_name, data_field_value, is_common_data, source_file, upload_date_time, record_start_date_time, record_end_date_time) VALUES(nextval('manufacturer_data_manufacturer_data_id_seq'), 'PD1', 'Temp', '8.40', true, 'file_1', '2023-09-06 00:00:01.000001', '2023-09-06 00:00:01.000001', '9999-01-01 00:00:01.000001');
--INSERT INTO manufacturer_data(manufacturer_data_id, producer_username, data_field_name, data_field_value, is_common_data, source_file, upload_date_time, record_start_date_time, record_end_date_time) VALUES(nextval('manufacturer_data_manufacturer_data_id_seq'), 'PD1', 'Humidity', '45', true, 'file_1', '2023-09-06 00:00:01.000001', '2023-09-06 00:00:01.000001', '9999-01-01 00:00:01.000001');
--INSERT INTO manufacturer_data(manufacturer_data_id, producer_username, data_field_name, data_field_value, is_common_data, source_file, upload_date_time, record_start_date_time, record_end_date_time) VALUES(nextval('manufacturer_data_manufacturer_data_id_seq'), 'PD1', 'Feedback', 'Good! On Time', false, 'file_1', '2023-09-06 00:00:01.000001', '2023-09-06 00:00:01.000001', '9999-01-01 00:00:01.000001');
--INSERT INTO manufacturer_data(manufacturer_data_id, producer_username, data_field_name, data_field_value, is_common_data, source_file, upload_date_time, record_start_date_time, record_end_date_time) VALUES(nextval('manufacturer_data_manufacturer_data_id_seq'), 'PD2', 'Distributor ID', 'D2', false, 'file_1', '2023-09-06 00:00:01.000001', '2023-09-06 00:00:01.000001', '9999-01-01 00:00:01.000001');
--INSERT INTO manufacturer_data(manufacturer_data_id, producer_username, data_field_name, data_field_value, is_common_data, source_file, upload_date_time, record_start_date_time, record_end_date_time) VALUES(nextval('manufacturer_data_manufacturer_data_id_seq'), 'PD2', 'Name', 'TUK', true, 'file_1', '2023-09-06 00:00:01.000001', '2023-09-06 00:00:01.000001', '9999-01-01 00:00:01.000001');
--INSERT INTO manufacturer_data(manufacturer_data_id, producer_username, data_field_name, data_field_value, is_common_data, source_file, upload_date_time, record_start_date_time, record_end_date_time) VALUES(nextval('manufacturer_data_manufacturer_data_id_seq'), 'PD2', 'Delivery Date', '22-07-2023', true, 'file_1', '2023-09-06 00:00:01.000001', '2023-09-06 00:00:01.000001', '9999-01-01 00:00:01.000001');
--INSERT INTO manufacturer_data(manufacturer_data_id, producer_username, data_field_name, data_field_value, is_common_data, source_file, upload_date_time, record_start_date_time, record_end_date_time) VALUES(nextval('manufacturer_data_manufacturer_data_id_seq'), 'PD2', 'Milk Volume (liters)', '500', true, 'file_1', '2023-09-06 00:00:01.000001', '2023-09-06 00:00:01.000001', '9999-01-01 00:00:01.000001');
--INSERT INTO manufacturer_data(manufacturer_data_id, producer_username, data_field_name, data_field_value, is_common_data, source_file, upload_date_time, record_start_date_time, record_end_date_time) VALUES(nextval('manufacturer_data_manufacturer_data_id_seq'), 'PD2', 'Temp', '8.40', true, 'file_1', '2023-09-06 00:00:01.000001', '2023-09-06 00:00:01.000001', '9999-01-01 00:00:01.000001');
--INSERT INTO manufacturer_data(manufacturer_data_id, producer_username, data_field_name, data_field_value, is_common_data, source_file, upload_date_time, record_start_date_time, record_end_date_time) VALUES(nextval('manufacturer_data_manufacturer_data_id_seq'), 'PD2', 'Humidity', '52', true, 'file_1', '2023-09-06 00:00:01.000001', '2023-09-06 00:00:01.000001', '9999-01-01 00:00:01.000001');
--INSERT INTO manufacturer_data(manufacturer_data_id, producer_username, data_field_name, data_field_value, is_common_data, source_file, upload_date_time, record_start_date_time, record_end_date_time) VALUES(nextval('manufacturer_data_manufacturer_data_id_seq'), 'PD2', 'Feedback', 'Late Delivery', false, 'file_1', '2023-09-06 00:00:01.000001', '2023-09-06 00:00:01.000001', '9999-01-01 00:00:01.000001');