ALTER TABLE `person`
    ADD COLUMN `author` BIT(1) NOT NULL DEFAULT b'0' AFTER `gender`;