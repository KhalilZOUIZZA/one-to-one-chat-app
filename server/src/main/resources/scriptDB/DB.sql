-- Create the database
CREATE DATABASE IF NOT EXISTS chat_app_db;

-- Use the database
USE chat_app_db;
SET autocommit = 0;

-- Table for Compte
CREATE TABLE IF NOT EXISTS Account (
    email_id VARCHAR(255) PRIMARY KEY,
    creation_date DATE
);

-- Table for Utilisateur
CREATE TABLE IF NOT EXISTS User (
    last_name VARCHAR(255),
    first_name VARCHAR(255),
    email_id VARCHAR(255) PRIMARY KEY,
    FOREIGN KEY (email_id) REFERENCES Account(email_id)
);

-- Table invite la liste des personnes que j'ai invité
CREATE TABLE IF NOT EXISTS Guest (
    inviter_email_id VARCHAR(255),
    Guest_email_id VARCHAR(255),
    PRIMARY KEY (inviter_email_id, Guest_email_id),
    FOREIGN KEY (inviter_email_id) REFERENCES User(email_id),
    FOREIGN KEY (Guest_email_id) REFERENCES User(email_id)
);

-- Table Invitant la liste des personnes qui m'ont invité
CREATE TABLE IF NOT EXISTS Inviter (
    Guest_email_id VARCHAR(255),
    inviter_email_id VARCHAR(255),
    PRIMARY KEY (Guest_email_id, inviter_email_id),
    FOREIGN KEY (Guest_email_id) REFERENCES User(email_id),
    FOREIGN KEY (inviter_email_id) REFERENCES User(email_id)
);

-- Table for Contact liste des contacts de tout les utilisateurs
CREATE TABLE IF NOT EXISTS Contact (
    interlocutor1_email_id VARCHAR(255),
    interlocutor2_email_id VARCHAR(255),
    PRIMARY KEY (interlocutor1_email_id, interlocutor2_email_id ),
    FOREIGN KEY (interlocutor1_email_id) REFERENCES User(email_id),
    FOREIGN KEY (interlocutor2_email_id) REFERENCES User(email_id)
);

-- Table for Authentification (hashed password)
CREATE TABLE IF NOT EXISTS Authentication(
    email_id VARCHAR(255) PRIMARY KEY,
    password TEXT
);

-- Table for Discussion
CREATE TABLE IF NOT EXISTS Discussion(
    participant1 VARCHAR(255),
    participant2 VARCHAR(255),
    PRIMARY KEY (participant1, participant2 ),
    FOREIGN KEY (participant1, participant2) REFERENCES Contact(interlocutor1_email_id, interlocutor2_email_id)
);

-- Table for Message
CREATE TABLE IF NOT EXISTS Message(
    message_id INT PRIMARY KEY AUTO_INCREMENT,
    participant1 VARCHAR(255),
    participant2 VARCHAR(255),
    sender VARCHAR(255),
    sending_date DATE,
    text VARCHAR(255),
    seen BOOLEAN,
    sentToUser BOOLEAN,
    sentToServer BOOLEAN,
    FOREIGN KEY (participant1, participant2) REFERENCES Discussion(participant1, participant2)
);

commit ;