UPDATE person SET first_name = CONCAT(first_name, ' ' , last_name);
ALTER TABLE person DROP COLUMN last_name
