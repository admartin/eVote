DROP TABLE if exists ElectionsOfficer;
DROP TABLE if exists Candidate;
DROP TABLE if exists Record;
DROP TABLE if exists PoliticalParty;
DROP TABLE if exists Voter;
DROP TABLE if exists BallotItem;
DROP TABLE if exists Ballot;
DROP TABLE if exists ElectoralDistrict;
DROP TABLE if exists User;
DROP TABLE if exists Election;
DROP TABLE if exists Issue;


CREATE TABLE Election (
 electionID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
 office CHAR(20),
 isPartison BIT(1) NOT NULL
);

CREATE TABLE ElectoralDistrict (
 edID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
 name CHAR(20)
);

CREATE TABLE Issue (
 issueID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
 question CHAR(100),
 yesCount INT,
 noCount INT
);

CREATE TABLE PoliticalParty (
 partyName CHAR(15) NOT NULL
);

ALTER TABLE PoliticalParty ADD CONSTRAINT PK_PoliticalParty PRIMARY KEY (partyName);


CREATE TABLE User (
 userID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
 username CHAR(20) NOT NULL UNIQUE,
 pass CHAR(20),
 email CHAR(30) NOT NULL
);

CREATE TABLE Voter (
 voterID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
 username CHAR(20) NOT NULL,
 password CHAR(20) NOT NULL,
 age INT NOT NULL,
 fName CHAR(15) NOT NULL,
 lName CHAR(15) NOT NULL,
 address CHAR(50) NOT NULL,
 district int NOT NULL
);

CREATE TABLE Ballot (
 ballotID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
 openDate char(35),
 closeDate char(35),
 edID INT NOT NULL
);


CREATE TABLE BallotItem (
 itemID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
 voteCount INT DEFAULT 0,
 ballotID INT NOT NULL,
 electionID INT NOT NULL,
 issueID INT NOT NULL
);


CREATE TABLE Candidate (
 candidateid INT UNSIGNED PRIMARY KEY auto_increment,
 name CHAR(20) NOT NULL,
 voteCount INT,
 electionID INT NOT NULL,
 partyName CHAR(15) NOT NULL
);


CREATE TABLE ElectionsOfficer (
 eoID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
 username CHAR(20) NOT NULL,
 pass CHAR(20) NOT NULL,
 fName CHAR(15),
 lName CHAR(10)
);


CREATE TABLE Record (
 recordID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
 date DATE,
 itemID INT NOT NULL,
 voterID INT NOT NULL
);


ALTER TABLE Voter ADD CONSTRAINT FK_Voter_0 FOREIGN KEY (username) REFERENCES User (username);


INSERT INTO User ( username, pass, email)
VALUES ( 'admin', 'adminpass', 'admin@host.com');

INSERT INTO electionsofficer ( username, pass, fname,lname)
VALUES ( 'admin', 'adminpass', 'admin', 'admin');
