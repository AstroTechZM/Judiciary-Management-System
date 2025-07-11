SET NAMES utf8mb4;
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS JMS;
CREATE SCHEMA JMS;
USE JMS;




-- Disable foreign key checks for clean creation if tables might exist already
-- Not strictly necessary for a fresh creation, but useful for re-running scripts.
SET FOREIGN_KEY_CHECKS = 0;

-- 1. Users Table
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL, -- Store hashed passwords, never plain text
    role VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- Domain Constraint for 'role'
    CONSTRAINT CHK_UserRole CHECK (role IN ('Judge', 'Clerk', 'Lawyer', 'PublicUser'))
);

-- 2. Cases Table
CREATE TABLE Cases (
    case_id INT AUTO_INCREMENT PRIMARY KEY,
    case_number VARCHAR(50) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    judge_id INT, -- Can be NULL if a case doesn't have a judge assigned initially, or if judge can be unassigned
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    -- Domain Constraint for 'status'
    CONSTRAINT CHK_CaseStatus CHECK (status IN ('Pending', 'Ongoing', 'Closed')),
    -- Foreign Key Constraint for 'judge_id'
    CONSTRAINT FK_Cases_Judge
        FOREIGN KEY (judge_id)
        REFERENCES Users(user_id)
        ON DELETE RESTRICT -- Prevent deleting a judge if they are assigned to a case
        ON UPDATE CASCADE -- Update judge_id in Cases if user_id changes in Users
);

-- 3. Documents Table
CREATE TABLE Documents (
    document_id INT AUTO_INCREMENT PRIMARY KEY,
    case_id INT NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    uploaded_by INT NOT NULL,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version_number INT NOT NULL DEFAULT 1,
    -- Foreign Key Constraint for 'case_id'
    CONSTRAINT FK_Documents_Case
        FOREIGN KEY (case_id)
        REFERENCES Cases(case_id)
        ON DELETE CASCADE -- If a case is deleted, its documents are also deleted
        ON UPDATE CASCADE,
    -- Foreign Key Constraint for 'uploaded_by'
    CONSTRAINT FK_Documents_Uploader
        FOREIGN KEY (uploaded_by)
        REFERENCES Users(user_id)
        ON DELETE RESTRICT -- Prevent deleting a user if they have uploaded documents
        ON UPDATE CASCADE
);

-- 4. Hearings Table
CREATE TABLE Hearings (
    hearing_id INT AUTO_INCREMENT PRIMARY KEY,
    case_id INT NOT NULL,
    hearing_date DATETIME NOT NULL,
    location VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    -- Domain Constraint for 'status'
    CONSTRAINT CHK_HearingStatus CHECK (status IN ('Scheduled', 'Completed', 'Adjourned')),
    -- Foreign Key Constraint for 'case_id'
    CONSTRAINT FK_Hearings_Case
        FOREIGN KEY (case_id)
        REFERENCES Cases(case_id)
        ON DELETE CASCADE -- If a case is deleted, its hearings are also deleted
        ON UPDATE CASCADE
);

-- 5. Efiling Table
CREATE TABLE Efiling (
    efiling_id INT AUTO_INCREMENT PRIMARY KEY,
    case_id INT NOT NULL,
    submitted_by INT NOT NULL,
    submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    -- Domain Constraint for 'status'
    CONSTRAINT CHK_EfilingStatus CHECK (status IN ('Accepted', 'Pending', 'Rejected')),
    -- Foreign Key Constraint for 'case_id'
    CONSTRAINT FK_Efiling_Case
        FOREIGN KEY (case_id)
        REFERENCES Cases(case_id)
        ON DELETE CASCADE -- If a case is deleted, its efilings are also deleted
        ON UPDATE CASCADE,
    -- Foreign Key Constraint for 'submitted_by'
    CONSTRAINT FK_Efiling_Submitter
        FOREIGN KEY (submitted_by)
        REFERENCES Users(user_id)
        ON DELETE RESTRICT -- Prevent deleting a user if they have submitted efilings
        ON UPDATE CASCADE
);

-- 6. AuditLogs Table
CREATE TABLE AuditLogs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT, -- Can be NULL if action is system-generated or user deleted later
    action TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- Foreign Key Constraint for 'user_id'
    CONSTRAINT FK_AuditLogs_User
        FOREIGN KEY (user_id)
        REFERENCES Users(user_id)
        ON DELETE SET NULL -- If a user is deleted, their log entries remain, but user_id becomes NULL
        ON UPDATE CASCADE
);

-- Re-enable foreign key checks after table creation
SET FOREIGN_KEY_CHECKS = 1;
