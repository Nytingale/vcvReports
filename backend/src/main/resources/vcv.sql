DROP DATABASE IF EXISTS vcv;
CREATE DATABASE vcv;
USE vcv;

CREATE TABLE `user`(
    `email` VARCHAR(32) NOT NULL,
    `password` VARCHAR(64) NOT NULL,
    `password_reset` TINYINT(1) DEFAULT 0,
    `role_id` BIGINT DEFAULT 1,
    `company_id` BIGINT NOT NULL,
    INDEX(role_id),
    INDEX(company_id),
    PRIMARY KEY(email)
) ENGINE = InnoDB;

CREATE TABLE `role`(
    `id` BIGINT NOT NULL,
    `name` VARCHAR(32) NOT NULL,
    `user` TINYINT(1) DEFAULT 0,
    `admin` TINYINT(1) DEFAULT 0,
    `staff` TINYINT(1) DEFAULT 0,
    PRIMARY KEY(id)
) ENGINE = InnoDB;

CREATE TABLE `company`(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `company_name` VARCHAR(64) NOT NULL,
    `company_type` ENUM('VCVStaff', 'Dealership', 'Insurance', 'Garage', 'Mechanic', 'Casual') DEFAULT 'Casual',
    `subscription_start_date` TIMESTAMP NOT NULL,
    `subscription_end_date` TIMESTAMP NOT NULL,
    `rating` INT DEFAULT 0,
    `website` VARCHAR(64) DEFAULT '',
    `admin` VARCHAR(64) NOT NULL,
    `blacklisted` TINYINT(1) DEFAULT 0,
    `valid` TINYINT(1) DEFAULT 1,
    INDEX(company_name),
    INDEX(company_type),
    INDEX(website),
    INDEX(admin),
    PRIMARY KEY(id)
) ENGINE = InnoDB;

CREATE TABLE `policy`(
    `company_id` BIGINT NOT NULL,
    `policy_number` VARCHAR(64) NOT NULL,
    `policy_owner` VARCHAR(64) NOT NULL,
    `policy_type` ENUM('ThirdParty', 'Comprehensive') DEFAULT 'Comprehensive',
    `policy_date` TIMESTAMP NOT NULL,
    `financer` VARCHAR(64) DEFAULT '',
    `valid` TINYINT(1) DEFAULT 1,
    `vin` VARCHAR(17) NOT NULL,
    INDEX(vin),
    INDEX(company_id),
    INDEX(policy_date),
    PRIMARY KEY(company_id, policy_number)
) ENGINE = InnoDB;

CREATE TABLE `claim`(
    `company_id` BIGINT NOT NULL,
    `claim_number` VARCHAR(64) NOT NULL,
    `claim_type` ENUM('PersonalInjury', 'TotalLoss', 'Liability', 'Accident') DEFAULT 'Accident',
    `claim_date` TIMESTAMP NOT NULL,
    `claim_details` TEXT DEFAULT '',
    `value` BIGINT NOT NULL,
    `policy_number` VARCHAR(64) NOT NULL,
    `vin` VARCHAR(17) NOT NULL,
    `job_id` BIGINT DEFAULT 0,
    INDEX(vin),
    INDEX(job_id),
    INDEX(claim_date),
    INDEX(company_id),
    PRIMARY KEY(company_id, claim_number)
) ENGINE = InnoDB;

CREATE TABLE `job`(
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `job_type` ENUM('Accident', 'Service', 'Upgrade', 'Repair') DEFAULT 'Repair',
    `job_date` TIMESTAMP NOT NULL,
    `job_cost` INT NOT NULL,
    `job_details` TEXT DEFAULT '',
    `company_id` BIGINT NOT NULL,
    `insurance_id` BIGINT DEFAULT 0,
    `claim_number` VARCHAR(64) DEFAULT '',
    `vin` VARCHAR(17) NOT NULL,
    INDEX(vin),
    INDEX(job_date),
    INDEX(company_id),
    INDEX(claim_number, insurance_id),
    PRIMARY KEY(id)
) ENGINE = InnoDB;

CREATE TABLE `vehicle`(
    `vin` VARCHAR(17) NOT NULL,
    `year` INT NOT NULL,
    `make` VARCHAR(64) NOT NULL,
    `model` VARCHAR(64) NOT NULL,
    `colour` VARCHAR(64),
    `value` INT NOT NULL,
    `mileage` INT,
    `dealership` BIGINT NOT NULL,
    `manufacturer` VARCHAR(64),
    `transmission` VARCHAR(64),
    `fuel_type` VARCHAR(64),
    `steering` VARCHAR(64),
    `engine` VARCHAR(64),
    `drive` VARCHAR(64),
    `body` VARCHAR(64),
    `num_seats` INT,
    `num_doors` INT,
    `written_off` TINYINT(1) DEFAULT 0,
    `stolen` TINYINT(1) DEFAULT 0,
    `evaluation_date` TIMESTAMP NOT NULL,
    `registration_date` TIMESTAMP NOT NULL,
    `num_accidents` INT DEFAULT 0,
    `num_robberies` INT DEFAULT 0,
    `num_services` INT DEFAULT 0,
    `num_salvages` INT DEFAULT 0,
    `num_owners` INT DEFAULT 1,
    `insurance_id` BIGINT,
    `policy_number` VARCHAR(64),
    INDEX(evaluation_date),
    INDEX(registration_date),
    INDEX(year, make, model),
    INDEX(policy_number, insurance_id),
    PRIMARY KEY(vin)
) ENGINE = InnoDB;

ALTER TABLE `job`     ADD CONSTRAINT `job_vehicle_fk`    FOREIGN KEY(vin)                         REFERENCES `vehicle`(vin);
ALTER TABLE `user`    ADD CONSTRAINT `user_role_fk`      FOREIGN KEY(role_id)                     REFERENCES `role`(id);
ALTER TABLE `user`    ADD CONSTRAINT `user_company_fk`   FOREIGN KEY(company_id)                  REFERENCES `company`(id);
ALTER TABLE `claim`   ADD CONSTRAINT `claim_policy_fk`   FOREIGN KEY(company_id, policy_number)   REFERENCES `policy`(company_id, policy_number);
ALTER TABLE `claim`   ADD CONSTRAINT `claim_vehicle_fk`  FOREIGN KEY(vin)                         REFERENCES `vehicle`(vin);
ALTER TABLE `policy`  ADD CONSTRAINT `policy_vehicle_fk` FOREIGN KEY(vin)                         REFERENCES `vehicle`(vin);

# ==============================================================
# = Adding in roles
# ==============================================================
INSERT INTO `role` VALUES (1, 'user',  1, 0, 0);       # = user
INSERT INTO `role` VALUES (2, 'Admin', 1, 1, 0);       # = Admin
INSERT INTO `role` VALUES (3, 'Staff', 1, 1, 1);       # = Staff

# ==============================================================
# = Adding VCV as a company
# ==============================================================
INSERT INTO `company` VALUES (1, 'VCV', 'VCVStaff', '2019-04-01 00:00:00', '2020-04-01 00:00:00', 5, 'https://vcv.com', 'RSJMorris@gmail.com', 0, 1);