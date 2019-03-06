DROP DATABASE IF EXISTS vcv;
CREATE DATABASE vcv;
USE vcv;

CREATE TABLE Users(
    email VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL,
    company_name VARCHAR(64) NOT NULL,
    company_type ENUM('VCV Staff', 'Delearship', 'Insurance', 'Garage', 'Mechanic', 'Casual') DEFAULT 'Casual',
    subscription_start_date TIMESTAMP NOT NULL,
    subscription_end_date TIMESTAMP NOT NULL,
    blacklisted TINYINT(1) DEFAULT 0,
    admin TINYINT(1) DEFAULT 1,
    valid TINYINT(1) DEFAULT 1,
    INDEX(email, company_name),
    INDEX(subscription_start_date, subscription_end_date),
    PRIMARY KEY(email, company_name)
) ENGINE = InnoDB;

CREATE TABLE Policies(
    policy_number VARCHAR(64) NOT NULL,
    company_name VARCHAR(64) NOT NULL,
    policy_owner VARCHAR(64) NOT NULL,
    policy_type ENUM('Third Party', 'Comprehensive') DEFAULT 'Comprehensive',
    policy_date TIMESTAMP NOT NULL,
    financer VARCHAR(64),
    valid TINYINT(1) DEFAULT 1,
    vin VARCHAR(17) NOT NULL,
    INDEX(vin),
    INDEX(policy_date),
    PRIMARY KEY(company_name, policy_number)
) ENGINE = InnoDB;

CREATE TABLE Claims(
    company_name VARCHAR(64) NOT NULL,
    claim_number VARCHAR(64) NOT NULL,
    claim_type ENUM('Personal Injury', 'Total Loss', 'Liability', 'Accident') DEFAULT 'Accident',
    claim_date TIMESTAMP NOT NULL,
    claim_details TEXT,
    value BIGINT NOT NULL,
    policy_number VARCHAR(64) NOT NULL,
    vin VARCHAR(17) NOT NULL,
    job_id BIGINT,
    INDEX(vin),
    INDEX(job_id),
    INDEX(claim_date),
    PRIMARY KEY(company_name, claim_number)
) ENGINE = InnoDB;

CREATE TABLE Jobs(
    job_id BIGINT AUTO_INCREMENT NOT NULL,
    job_type ENUM('Accident', 'Service', 'Upgrade', 'Repair') DEFAULT 'Repair',
    job_date TIMESTAMP NOT NULL,
    job_cost INT NOT NULL,
    job_details TEXT,
    company_name VARCHAR(64) NOT NULL,
    claim_number VARCHAR(64),
    insurance_name VARCHAR(64),
    vin VARCHAR(17) NOT NULL,
    INDEX(vin),
    INDEX(job_date),
    INDEX(claim_number, insurance_name),
    PRIMARY KEY(job_id)
) ENGINE = InnoDB;

CREATE TABLE Vehicles(
    vin VARCHAR(17) NOT NULL,
    year INT NOT NULL,
    make VARCHAR(64) NOT NULL,
    model VARCHAR(64) NOT NULL,
    manufacturer VARCHAR(64) NOT NULL,
    engine VARCHAR(64) NOT NULL,
    colour VARCHAR(64) NOT NULL,
    mileage INT NOT NULL,
    transmission VARCHAR(64) NOT NULL,
    value INT NOT NULL,
    dealership VARCHAR(64) NOT NULL,
    written_off TINYINT(1) DEFAULT 0,
    stolen TINYINT(1) DEFAULT 0,
    evaluation_date TIMESTAMP NOT NULL,
    registration_date TIMESTAMP NOT NULL,
    num_accidents INT DEFAULT 0,
    num_robberies INT DEFAULT 0,
    num_services INT DEFAULT 0,
    num_salvages INT DEFAULT 0,
    num_owners INT DEFAULT 0,
    insurance_name VARCHAR(64) NOT NULL,
    policy_number VARCHAR(64) NOT NULL,
    INDEX(evaluation_date),
    INDEX(registration_date),
    INDEX(year, make, model),
    INDEX(policy_number, insurance_name),
    PRIMARY KEY(vin)
) ENGINE = InnoDB;

ALTER TABLE Jobs     ADD CONSTRAINT jobs_claims_fk       FOREIGN KEY(claim_number, insurance_name)  REFERENCES Claims(company_name, claim_number);
ALTER TABLE Jobs     ADD CONSTRAINT jobs_vehicles_fk     FOREIGN KEY(vin)                           REFERENCES Vehicles(vin);
ALTER TABLE Claims   ADD CONSTRAINT claims_jobs_fk       FOREIGN KEY(job_id)                        REFERENCES Jobs(job_id);
ALTER TABLE Claims   ADD CONSTRAINT claims_policies_fk   FOREIGN KEY(company_name, policy_number)   REFERENCES Policies(company_name, policy_number);
ALTER TABLE Claims   ADD CONSTRAINT claims_vehicles_fk   FOREIGN KEY(vin)                           REFERENCES Vehicles(vin);
ALTER TABLE Policies ADD CONSTRAINT policies_vehicles_fk FOREIGN KEY(vin)                           REFERENCES Vehicles(vin);
ALTER TABLE Vehicles ADD CONSTRAINT vehicles_policies_fk FOREIGN KEY(policy_number, insurance_name) REFERENCES Policies(company_name, policy_number);

# ==============================================================
# = Dummy Data for Demo
# ==============================================================
INSERT INTO Users VALUES
    ('RSJMorris@gmail.com', 'Vehicle Curriculum Vitae', 'VCVStaff', 'VCV Staff', '2019-01-01 00:00:00', '2020-01-01 00:00:00', 0, 1, 1),
    ('danielboyce@me.com', 'CGI Insurance', 'admin', 'Insurance', '2018-12-01 00:00:00', '2019-12-01 00:00:00', 0, 1, 1),
    ('mr.prphillips@gmail.com', 'CGI Insurance', 'user', 'Insurance', '2018-12-01 00:00:00', '2019-12-01 00:00:00', 0, 0, 1);
